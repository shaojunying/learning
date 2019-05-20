import Utils.Data;
import Utils.Helper;
import Utils.MessageType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shao on 2018/12/4.
 */
public class Server {
    private static Map<Socket, String> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Helper.serverPort);
        Socket client;
        while (true) {
            client = serverSocket.accept();
            System.out.printf("与%d连接成功\n", client.getPort());
            new Thread(new ServerThread(client)).start();
        }
    }

    private static void sendUserList() {
        Map<Integer, String> Port2IdMap = new HashMap<>();
        clientMap.forEach((socket, userId1) -> Port2IdMap.put(socket.getPort(), userId1));
        Data userListData = new Data(MessageType.USER_INFO, Port2IdMap);
        clientMap.forEach((socket, userId1) -> {
            try {
                Helper.sendData(socket, userListData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static class ServerThread implements Runnable{
        // 当前监听的客户端
        private Socket client;
        boolean flag;

        ServerThread(Socket client) {
            this.client = client;
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
                        data = Helper.receiveData(client);
                    } catch (IOException e) {
                        flag = false;
                    }
                    if (data == null) {
                        continue;
                    }
                    if (data.getMessageType() == MessageType.ESTABLISH) {
                        /*
                         * 客户端请求建立连接或改名
                         * */
                        clientMap.put(client, data.getUserId());
                        System.out.printf("客户端%d用户名改为[%s]\n", client.getPort(), data.getUserId());
                        sendUserList();
                    } else if (data.getMessageType() == MessageType.PRIVATE_MESSAGE) {
                        /*
                         * 客户端发出私聊消息
                         * */
                        Data sendData = new Data(MessageType.PRIVATE_MESSAGE, client.getPort(), data.getContent(), data.getSendDate());
                        Data finalData = data;
                        clientMap.forEach((socket, userId) -> {
                            if (socket.getPort() == finalData.getPort()) {
                                try {
                                    // 向客户端发送私聊消息
                                    Helper.sendData(socket, sendData);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        /*
                         * 客户端发出群聊消息
                         * */
                        String userId = clientMap.get(client);
                        Data sendData = new Data(MessageType.PUBLIC_MESSAGE,
                                client.getPort(), data.getContent(), data.getSendDate());
                        System.out.printf("客户端%d(用户名%s)发消息[%s]\n", client.getPort(), userId, data.getContent());
                        clientMap.forEach((receiverSocket, receiverUserId) -> {
                            try {
                                Helper.sendData(receiverSocket, sendData);
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
