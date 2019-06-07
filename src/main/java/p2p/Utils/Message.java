package p2p.Utils;

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
public class Message implements Serializable {
    /*
     * 指定消息的类型,默认存储的是聊天记录
     * */
    private MessageType messageType = MessageType.MESSAGE;
    // 新userID
    private String userId;
    // 消息内容
    private String content;
    // 发送时间
    private Date sendDate = Calendar.getInstance().getTime();
    // ip:port
    private String address;
    // 端口到id之间映射的map
    private Map<String, String> address2IdMap;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Map<String, String> getAddress2IdMap() {
        return address2IdMap;
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

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }


    public void setAddress2IdMap(Map<String, String> address2IdMap) {
        this.address2IdMap = address2IdMap;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", sendDate=" + sendDate +
                ", address='" + address + '\'' +
                ", address2IdMap=" + address2IdMap +
                '}';
    }
}
