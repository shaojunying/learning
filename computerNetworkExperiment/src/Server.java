import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by shao on 2018/12/4.
 */
public class Server {

    public static class ServerThread implements Runnable{

        private Socket client;

        public ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                // 获取客户端的输出
                PrintStream out = new PrintStream(client.getOutputStream());
                // 获取客户端的输入
                BufferedReader bufferedInputStream =
                        new BufferedReader(new InputStreamReader(client.getInputStream()));
                boolean flag = true;
                while (flag){
                    String str = bufferedInputStream.readLine();
                    if (str == null||"".equals(str)){
                        flag=false;
                    }else {
                        if ("bye".equals(str)){
                            flag = false;
                        }else {
                            // 向客户端返回数据
                            out.println("echo:"+str);
                        }
                    }
                    System.out.println(flag);
                }
                System.out.println("客户端发送断开链接消息并成功断开");
            } catch (Exception e) {
                System.out.println("客户端强制断开链接");
//                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(20000);
        Socket client = null;
//        boolean f = true;
        while (true){
            client = serverSocket.accept();
            System.out.println("与客户端链接成功");
            new Thread(new ServerThread(client)).start();
        }
    }

}
