package Utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 用途:
 * 用于存储数据的结构体
 * <p>
 * Created by shao on 2018/12/10.
 */
public class Data implements Serializable {
    /*
     * 指定消息的类型,默认存储的是聊天记录
     * */
    private MessageType messageType = MessageType.MESSAGE;
    private String userId;
    private String content;
    private Date sendDate;
    private int port;
    private Map<Integer, String> Port2IdMap;

    public Data(MessageType messageType, Map<Integer, String> Port2IdMap) {
        /*
         * 向客户端发送用户列表信息
         * */
        this.messageType = messageType;
        this.Port2IdMap = Port2IdMap;
    }


    public Data(MessageType messageType, String userId) {
        /*
         * 客户端建立连接
         * */
        this.messageType = messageType;
        this.userId = userId;
    }

    public Data(int port, String content, Date sendDate) {
        /*
         * 向客户端发送某一条聊天记录
         * */
        this.port = port;
        this.content = content;
        this.sendDate = sendDate;
    }

    public Data(String content) {
        // 客户端产生一条聊天记录,发送给服务器
        this.content = content;
        sendDate = Calendar.getInstance().getTime();
    }

    public int getPort() {
        return port;
    }

    public Map<Integer, String> getPort2IdMap() {
        return Port2IdMap;
    }

    public String getContent() {
        return content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Data{" +
                "messageType=" + messageType +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
