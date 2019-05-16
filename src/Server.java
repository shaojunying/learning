import Utils.Data;
import Utils.Helper;
import Utils.MessageType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by shao on 2018/12/4.
 */
public class Server {
    static Map<Socket, String> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(20001);
        Socket client;
        while (true) {
            client = serverSocket.accept();
            System.out.printf("与%d连接成功\n", client.getPort());
            new Thread(new ServerThread(client)).start();
        }
    }

    public static void sendUserList() {
        List<String> userIdList = new LinkedList<>();
        clientMap.forEach((socket, userId1) -> userIdList.add(userId1));
        Data userListData = new Data(MessageType.USER_INFO, userIdList);
        clientMap.forEach((socket, userId1) -> {
            try {
                Helper.sendData(Helper.getSocketOutput(socket), userListData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static class ServerThread implements Runnable{
        private Socket client;
        DataInputStream dis;
        DataOutputStream dos;
        boolean flag;

        ServerThread(Socket client) throws IOException {
            this.client = client;
            // 获取客户端的输出
            dis = new DataInputStream(client.getInputStream());
            // 获取客户端的输入
            dos = new DataOutputStream(client.getOutputStream());
            flag = true;
        }
        @Override
        public void run() {
            ReadThread readThread = new ReadThread();
            new Thread(readThread).start();
        }

        class ReadThread implements Runnable{

            @Override
            public void run() {
                // 从客户端读数据
                while (flag){
                    Data data = null;
                    try {
                        data = Helper.receiveData(dis);
                    } catch (IOException e) {
                        flag = false;
                        System.out.println("客户端强制退出");
                    }
                    if (data != null){
                        if (data.getMessageType() == MessageType.ESTABLISH) {
                            clientMap.put(client, data.getUserId());
                            System.out.printf("客户端%d用户名改为[%s]\n", client.getPort(), data.getUserId());
                            sendUserList();
                            continue;
                        }
                        String userId = clientMap.get(client);
                        Data sendData = new Data(userId, data.getContent(), data.getSendDate());
                        System.out.printf("客户端%d(用户名%s)发消息[%s]\n", client.getPort(), userId, data.getContent());
                        clientMap.forEach((receiverSocket, receiverUserId) -> {
                            try {
                                DataOutputStream outputStream = Helper.getSocketOutput(receiverSocket);
                                Helper.sendData(outputStream, sendData);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
                // 从字典中删除断开连接的客户端
                String userId = clientMap.get(client);
                clientMap.remove(client);
                sendUserList();
                System.out.printf("客户端%d(用户名:%s)发送断开链接消息并成功断开\n", client.getPort(), userId);
            }
        }
    }

}
