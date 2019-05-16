import Utils.Data;
import Utils.Helper;
import Utils.MessageType;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * Created by shao on 2018/12/9.
 */
public class Client {
    private Socket client;

    LinkedList<Data> dataList = new LinkedList<>();

    private Client() throws IOException {
        client = new Socket("127.0.0.1",20001);

        DataOutputStream dos = Helper.getSocketOutput(client);

        // 给frame换一个样式
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("客户端");
        Container container = frame.getContentPane();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 显示用户的聊天记录
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setBounds(10, 10, 400, 400);
        container.add(scrollPanel);

        JTextArea chatRecordArea = new JTextArea();
        chatRecordArea.setBounds(10, 10, 400, 400);
        chatRecordArea.setEditable(false);
        chatRecordArea.setLineWrap(true); // 激活自动换行功能
        chatRecordArea.setWrapStyleWord(true);
        container.add(chatRecordArea);
        scrollPanel.setViewportView(chatRecordArea);
        // 保证滚动条在最下面
        DefaultCaret caret = (DefaultCaret) chatRecordArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


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
                Helper.sendData(dos, new Data(content));
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
        ReadThread readThread = new ReadThread(chatRecordArea);
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

    private void sendNewUserId(String userId) throws IOException {
        userId = userId.trim();
        DataOutputStream dos = Helper.getSocketOutput(client);
        Helper.sendData(dos, new Data(MessageType.ESTABLISH, userId));
    }

    class ReadThread implements Runnable{
        private final JTextArea textArea;
        /*
        * 从服务器中获取数据
        * */

        ReadThread(JTextArea textArea) {
            // 将获取的文本追加进该textArea中
            this.textArea = textArea;
        }

        @Override
        public void run() {
            // 从server接受数据
            while (true) {
                Data data = null;
                try {
                    DataInputStream dis = Helper.getSocketInput(client);
                    data = Helper.receiveData(dis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert data != null;
                dataList.add(data);
                String showText = Helper.dataListToString(dataList);
                textArea.setText(showText);
            }
        }
    }
}
