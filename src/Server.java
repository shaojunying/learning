import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shao on 2018/12/4.
 */
public class Server {


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
            dos = new DataOutputStream(client.getOutputStream());;
            flag = true;
        }
        @Override
        public void run() {

            ReadThread readThread = new ReadThread();
            new Thread(readThread).start();
            WriteThread writeThread = new WriteThread();
            new Thread(writeThread).start();
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
//                        e.printStackTrace();
                    }
                    if (data != null){
                        System.out.println("从客户端接受到的数据是: "+data);
                    }
                }
                System.out.println("客户端发送断开链接消息并成功断开");
            }
        }

        class WriteThread implements Runnable{

            @Override
            public void run() {
                // 向客户段写数据

//                while (flag){
//
//                    SimpleDateFormat sf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    try {
//                        Helper.sendData(dos,new Data((int) (Math.random()*100),sf.format(new Date())));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

            }
        }
    }



    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(20001);
        Socket client = null;
//        boolean f = true;
        while (true){
            client = serverSocket.accept();
            System.out.println("与客户端链接成功");
            new Thread(new ServerThread(client)).start();
        }
    }

}
