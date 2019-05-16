package Utils;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by shao on 2018/12/10.
 */
public class Helper {

    private static final int maxMessageLength = 1024;
    // 日期的格式
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /*
     * 将对象转化为byte数组
     * */
    private static byte[] encodeData(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.flush();
        // 得到data对应的byte数组
        return bos.toByteArray();
    }

    /*
     * 将子节数组转化为对象
     * */
    public static Object decodeData(byte[] bytes) throws IOException {

        Object object = null;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    // 发送数据
    public static void sendData(DataOutputStream dos, Data data) throws IOException {
        dos.write(Helper.encodeData(data));
    }

    public static Data receiveData(DataInputStream dis) throws IOException {
        byte[] bytes = new byte[maxMessageLength];
        dis.read(bytes);
        return (Data) decodeData(bytes);
    }

    public static DataOutputStream getSocketOutput(Socket socket) throws IOException {
        /*
         * 发出数据
         * */
        return new DataOutputStream(socket.getOutputStream());
    }

    public static DataInputStream getSocketInput(Socket socket) throws IOException {
        /*
         * 接收数据
         * */
        return new DataInputStream(socket.getInputStream());
    }

    public static String getRandomString() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; i++) {
            int number = random.nextInt(3);
            long result;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(new Random().nextInt(10));
                    break;
            }

        }
        return sb.toString();
    }

    public static String formatTime(Date date) {
        return sf.format(date);
    }

    public static String dataListToString(List<Data> dataList) {
        StringBuilder result = new StringBuilder();
        dataList.forEach((data) ->
                result.append(data.getUserId()).append(" ")
                        .append(formatTime(data.getSendDate())).append("\n").
                        append(data.getContent()).append("\n\n"));
        return result.toString();
    }

}
