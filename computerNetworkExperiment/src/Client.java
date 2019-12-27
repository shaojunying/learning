import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * 需求
 * 并发地与多个client程序通信
 * client与server之间传递具有多个子段的消息:int,string
 * server可以检测client的推出
 *
 * Created by shao on 2018/12/9.
 */
public class Client {

    class ReadThread implements Runnable{

        @Override
        public void run() {
            // 从server接受数据
        }
    }
    
    class WriteThread implements Runnable{

        @Override
        public void run() {
            // 向server发送数据
        }
    }

    public static void main(String[] args) throws Exception {
        Socket client = new Socket("127.0.0.1",20000);
        client.setSoTimeout(10000);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        // 获取客户端的输出
        PrintStream out = new PrintStream(client.getOutputStream());
        // 获取客户端的输入
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;

        while (flag){
            System.out.println("输入信息: ");
            // 从命令行中获取输入
            String str = input.readLine();
            // 将命令行的输入发送给server
            out.println(str);
            if ("bye".equals(str)){
                flag = false;
            }else {
                try {
                    String echo = bufferedReader.readLine();
                    System.out.println(echo);
                }catch (SocketTimeoutException e){
                    System.out.println("Time out, NO response");
                }
            }
        }
        input.close();
        client.close();

    }
}
