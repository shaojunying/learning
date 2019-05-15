import java.io.*;
import java.net.Socket;

/**
 * 需求
 * client与server之间传递具有多个子段的消息:int,string
 *
 * Created by shao on 2018/12/9.
 */
public class Client {

    private Socket client;
    // 控制台的输入
    private BufferedReader input;
    // client的输出
    private DataOutputStream dos;
    // client的输入
    private DataInputStream dis;
    private boolean flag = true;
    private Client() throws IOException {
        client = new Socket("127.0.0.1",20001);
        // 控制台的输入
        input = new BufferedReader(new InputStreamReader(System.in));
        // 获取client的输出
        dos = new DataOutputStream(client.getOutputStream());
        // 获取client的输入
        dis = new DataInputStream(client.getInputStream());

        // 启动从服务器段接受信息的线程
        ReadThread readThread = new ReadThread();
        new Thread(readThread).start();

        // 启动向服务器发送数据的线程
        WriteThread writeThread = new WriteThread();
        new Thread(writeThread).start();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        input.close();
        client.close();
    }

    class ReadThread implements Runnable{
        /*
        * 从服务器中获取数据
        * */
        @Override
        public void run() {
            // 从server接受数据
            while (flag) {
                Data data = null;
                try {
                    data  = Helper.receiveData(dis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(data);
            }
        }
    }

    class WriteThread implements Runnable{
        /*
        * 想服务器发送数据
        * */
        @Override
        public void run() {
            // 向server发送数据
            byte[]  bytes = null;

            while (flag) {
                System.out.println("输入信息: 一个字符串 ");
                // 从命令行中获取输入
                String str = null;
                try {
                    str = input.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Helper.sendData(dos,new Data(str));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
    }
}
