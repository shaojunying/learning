import Utils.Message;
import Utils.Helper;
import Utils.MessageType;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by shao on 2018/12/9.
 */
public class Client {
    // 服务器
    private Socket server;
    private DatagramSocket datagramSocket;

    // 用来存储聊天记录
    private List<Message> messageList = new LinkedList<>();
    // 用来存储在线的用户列表
    private Map<String, String> address2IdMap = new HashMap<>();

    private Client() throws IOException {
        server = new Socket(Helper.serverIp, Helper.serverPort);
        datagramSocket = new DatagramSocket(server.getLocalPort());
    }

    private void start() throws IOException {
        // 给frame换一个样式
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("客户端");
        Container container = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*显示用户的聊天记录*/
        JTextArea chatRecordArea = createTextAreaWithScroller(container, 10, 10, 400, 400);
        // 保证滚动条在最下面
        DefaultCaret caret = (DefaultCaret) chatRecordArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


        // 显示用户列表
        JTextArea userListArea = createTextAreaWithScroller(container, 410, 45, 100, 360);

        // 显示用户名
        JTextArea userIdArea = new JTextArea();
        userIdArea.setBounds(430, 10, 60, 20);
        String userId = Helper.getRandomString();
        userIdArea.setText(userId);
        sendNewUserId(userId);
        container.add(userIdArea);

        // 提交按钮
        JButton changeUserIdButton = new JButton("提交");
        changeUserIdButton.setBounds(500, 10, 60, 30);
        frame.getContentPane().add(changeUserIdButton);
        // 点击发送之后将信息发送给服务端
        changeUserIdButton.addActionListener(event -> {
            String newUserId = userIdArea.getText();
            try {
                sendNewUserId(newUserId);
            } catch (IOException e) {
                // 提交新id失败
                System.out.println("提交新id失败");
                e.printStackTrace();
            }
        });


        // 用户输入文本的文本框
        JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 450, 390, 30);
        container.add(textArea);


        // 发送按钮
        JButton submit = new JButton("发送");
        submit.setBounds(410, 450, 60, 30);
        frame.getContentPane().add(submit);
        // 点击发送之后将信息发送给服务端
        submit.addActionListener(event -> {
            String content = textArea.getText();
            try {
                assert content != null;
                textArea.setText("");
                content = content.trim();
                if (content.length() == 0)
                    return;
                for (Map.Entry<String,String> user : address2IdMap.entrySet()){
                    String receiverAddress = user.getKey();
                    String[] temp = receiverAddress.split(":");
                    assert temp.length == 2;
                    String ip = temp[0];
                    int port = Integer.parseInt(temp[1]);
                    // 构造要发送的消息
                    Message message = new Message();
                    message.setContent(content);
                    // 这里设置发送者的地址
                    String sendAddress = server.getLocalAddress().getHostAddress()+":"+server.getLocalPort();
                    message.setAddress(sendAddress);
                    byte[] buffer = Helper.encodeData(message);
                    DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length,InetAddress.getByName(ip),port);
                    datagramSocket.send(datagramPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 显示窗口
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);

        // 从服务器段接受信息
        ReadThread readThread = new ReadThread(chatRecordArea, userListArea);
        new Thread(readThread).start();

        // 从其他客户端接受UDP消息
        new Thread(() -> {
            while (true){
                byte[] buffer = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
                try {
                    datagramSocket.receive(datagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = (Message) Helper.decodeData(buffer);
                if (message.getMessageType().equals(MessageType.MESSAGE)){
                    // 接受到消息
                    messageList.add(message);
                    String showText = Helper.dataListToString(address2IdMap, messageList);
                    chatRecordArea.setText(showText);
                }
            }
        }).start();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
    }

    /*
     * 在container中创建一个指定x,y,width,height的textArea
     * */
    private JTextArea createTextAreaWithScroller(Container container, int x, int y, int width, int height) {
        JScrollPane ScrollPanel = new JScrollPane();
        ScrollPanel.setBounds(x, y, width, height);
        container.add(ScrollPanel);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(x, y, width, height);
        textArea.setEditable(false);
        textArea.setLineWrap(true); // 激活自动换行功能
        textArea.setWrapStyleWord(true);
        container.add(textArea);
        ScrollPanel.setViewportView(textArea);

        return textArea;
    }

    /*
     * 将该用户新设置的userId发送给服务器
     * */
    private void sendNewUserId(String userId) throws IOException {
        // 取出新设置的userId前后的空白字符
        userId = userId.trim();
        Message message = new Message();
        message.setMessageType(MessageType.ESTABLISH);
        message.setUserId(userId);
        // 将新设置的数据发送给服务器
        Helper.sendData(server, message);
    }

    class ReadThread implements Runnable{
        private final JTextArea textArea;
        private final JTextArea userListArea;
        /*
        * 从服务器中获取数据
        * */
        ReadThread(JTextArea textArea, JTextArea userListArea) {
            // 将获取的文本追加进该textArea中
            this.textArea = textArea;
            this.userListArea = userListArea;
        }

        @Override
        public void run() {
            // 从server接受数据
            while (true) {
                Message message = null;
                try {
                    message = Helper.receiveData(server);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert message != null;
                if (message.getMessageType() == MessageType.USER_LIST) {
                    // 获取到用户列表
                    StringBuffer userListString = new StringBuffer();
                    address2IdMap = message.getAddress2IdMap();
                    address2IdMap.forEach((socket, userId) -> userListString.append(userId).append("\n"));
                    // 更新在线用户列表中的用户名
                    userListArea.setText(userListString.toString());

                    // 更新聊天记录中的用户名
                    String showText = Helper.dataListToString(address2IdMap, messageList);
                    textArea.setText(showText);
                }
            }
        }
    }
}
