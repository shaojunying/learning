import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 用途:
 *  用于存储数据的结构体
 *
 * Created by shao on 2018/12/10.
 */
class Data implements Serializable {
    String content;
    Date sendDate;
    Data(String content, Date sendDate) {
        this.content = content;
        this.sendDate = sendDate;
    }

    Data(String content) {
        this.content = content;
        sendDate = Calendar.getInstance().getTime();
    }

    @Override
    public String toString() {
        return "content: "+content+" sendDate: "+sendDate;
    }
}
