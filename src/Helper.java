import java.io.*;

/**
 * Created by shao on 2018/12/10.
 */
class Helper {

    /*
    * 将对象转化为byte数组
    * */
    static byte[] encodeData(Object object) throws IOException {
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
    static Object decodeData(byte[] bytes) throws IOException {

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
    static void sendData(DataOutputStream dos,Data data) throws IOException {
        dos.write(Helper.encodeData(data));
    }

    static Data receiveData(DataInputStream dis) throws IOException {
        byte[] bytes = new byte[1024];
        dis.read(bytes);
        return (Data) decodeData(bytes);
    }

}
