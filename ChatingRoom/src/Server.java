import Utils.Helper;
import Utils.Message;
import Utils.MessageType;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shao on 2018/12/4.
 */
public class Server {


    private Map<Socket, String> clientMap;

    public Server(){
        clientMap = new HashMap<>();
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(Helper.serverPort);
        Socket client;
        while (true) {
            client = serverSocket.accept();
            System.out.printf("与%d连接成功\n", client.getPort());
            new Thread(new ServerThread(client)).start();
        }
    }

    public class ServerThread implements Runnable{
        private Socket client;
        boolean flag;

        ServerThread(Socket client) {
            this.client = client;
            flag = true;
        }
        @Override
        public void run() {
            // 从客户端读数据
            while (flag){
                Message message = null;
                try {
                    message = Helper.receiveData(client);
                } catch (IOException e) {
                    flag = false;
                }
                if (message != null){
                    if (message.getMessageType() == MessageType.ESTABLISH
                            || message.getMessageType() == MessageType.CHANGE_NAME) {
                        // 客户端请求建立连接或改名
                        clientMap.put(client, message.getUserId());
                        String address = client.getInetAddress().getHostAddress()+":"+client.getPort();
                        System.out.printf("客户端%s用户名改为[%s]\n", address, message.getUserId());
                        sendUserList();
                    }
                    else if (message.getMessageType() == MessageType.USER_LIST){
                        // 客户端请求当前在线的用户列表
                        sendUserList();
                    }else {
                        System.out.print("接受到错误的数据\n");
                    }
                }
            }
            // 从字典中删除断开连接的客户端
            String userId = clientMap.get(client);
            clientMap.remove(client);
            sendUserList();
            System.out.printf("客户端%d(用户名:%s)发送断开链接消息并成功断开\n", client.getPort(), userId);
        }
    }
    private void sendUserList() {

        /*
        * 向所有在线用户发送在线用户列表
        * Map<Port,Id>: 每个用户的端口,每个用户的id
         * */
        System.out.println("向所有用户更新在线用户列表");
        Message message = new  Message();
        message.setMessageType(MessageType.USER_LIST);
        Map<String,String> address2IdMap = new HashMap<>();
        clientMap.forEach((socket, userId1) -> {
            String address = socket.getInetAddress().getHostAddress()+":"+socket.getPort();
            address2IdMap.put(address, userId1);
        });
        message.setAddress2IdMap(address2IdMap);
        clientMap.forEach((socket,userId1)->{
            try {
                Helper.sendData(socket,message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }

}
