package Utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用途:
 * 用于存储数据的结构体
 * <p>
 * Created by shao on 2018/12/10.
 */
public class Data implements Serializable {
    private MessageType messageType = MessageType.MESSAGE;
    private String userId;
    private String content;
    private Date sendDate;

    private List<String> userList;

    public Data(MessageType messageType, List<String> userList) {
        this.messageType = messageType;
        this.userList = userList;
    }

    public List<String> getUserList() {
        return userList;
    }

    public Data(MessageType messageType, String userId) {
        this.messageType = messageType;
        this.userId = userId;
    }

    public Data(String userId, String content, Date sendDate) {
        this.userId = userId;
        this.content = content;
        this.sendDate = sendDate;
    }

    public Data(String content) {
        this.content = content;
        sendDate = Calendar.getInstance().getTime();
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
