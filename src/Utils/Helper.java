package Utils;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by shao on 2018/12/10.
 */
public class Helper {

    public final static String serverIp = "";
    // 日期的格式
    private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static int serverPort = 20001;
    /*
     * 最大消息长度
     * */
    private static final int maxMessageLength = 1024;

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
    public static Object decodeData(byte[] bytes) {

        Object object = null;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /*
     * 向指定socket发送数据
     * */
    public static void sendData(Socket socket, Data data) throws IOException {
        DataOutputStream dos = getSocketOutput(socket);
        dos.write(Helper.encodeData(data));
    }

    /*
     * 从指定socket接受数据
     * */
    public static Data receiveData(Socket socket) throws IOException {
        DataInputStream dis = getSocketInput(socket);
        byte[] bytes = new byte[maxMessageLength];
        dis.read(bytes);
        return (Data) decodeData(bytes);
    }

    /*
     * 获取socket对应的outputStream(发送数据)
     * */
    private static DataOutputStream getSocketOutput(Socket socket) throws IOException {
        return new DataOutputStream(socket.getOutputStream());
    }

    /*
     * 获取socket对应的inputStream(接收数据)
     * */
    private static DataInputStream getSocketInput(Socket socket) throws IOException {
        return new DataInputStream(socket.getInputStream());
    }

    /*
     * 获取一个长度为5的随机串,串中可以含数字,大小写字母
     * */
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

    /*
     * 将Date对象进行格式化
     * */
    public static String formatTime(Date date) {
        return sf.format(date);
    }

    /*
     * 将聊天记录的dataList转化为String进行显示
     * */
    public static String dataListToString(Map<Integer, String> port2IdMap, List<Data> dataList) {
        StringBuilder result = new StringBuilder();
        dataList.forEach((data) -> {
            result.append(port2IdMap.get(data.getPort())).append(" ")
                        .append(formatTime(data.getSendDate())).append("\n").
                    append(data.getContent()).append("\n\n");
        });
        return result.toString();
    }

}
