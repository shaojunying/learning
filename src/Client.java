import Utils.Data;
import Utils.Helper;
import Utils.MessageType;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;
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
    private Socket client;

    // 用来存储聊天记录
    private List<Data> dataList = new LinkedList<>();
    // 用来存储在线的用户列表
    private Map<Integer, String> port2IdMap = new HashMap<>();


    private Client() throws IOException {
        client = new Socket(Helper.serverIp, Helper.serverPort);

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
                content = content.trim();
                if (content.length() == 0)
                    return;
                Helper.sendData(client, new Data(content));
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
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        client.close();
    }

    public static void main(String[] args) throws Exception {
        new Client();
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
        Helper.sendData(client, new Data(MessageType.ESTABLISH, userId));
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
                Data data = null;
                try {
                    data = Helper.receiveData(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert data != null;
                if (data.getMessageType() == MessageType.USER_INFO) {
                    StringBuffer userListString = new StringBuffer();
                    port2IdMap = data.getPort2IdMap();
                    port2IdMap.forEach((port, userId) -> {
                        userListString.append(userId).append("\n");
                    });
                    userListArea.setText(userListString.toString());
                    String showText = Helper.dataListToString(port2IdMap, dataList);
                    textArea.setText(showText);
                    continue;
                }
                dataList.add(data);
                String showText = Helper.dataListToString(port2IdMap, dataList);
                textArea.setText(showText);
            }
        }
    }
}
