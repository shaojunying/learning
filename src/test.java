import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Created by shao on 2019/5/24 17:47.
 */
public class test {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("",20001);
        Socket socket1 = new Socket("",20001);
        System.out.println(socket);
        System.out.println(socket1);
        System.out.println(socket.equals(socket1));

//        LinkedList<String> stringLinkedList = new LinkedList<>();
//        stringLinkedList.add("111");
//        Data data = new Data(
//                stringLinkedList
//        );
//        String string = JSON.toJSONString(data);
//        System.out.println(string);
//        Data data1 = JSON.parseObject(string, Data.class);
//        System.out.println(data1);
    }
}
