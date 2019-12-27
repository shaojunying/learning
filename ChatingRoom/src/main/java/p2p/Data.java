package p2p;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by shao on 2019/5/24 18:01.
 */
public class Data implements Serializable {
    int type = 1;
    Object data;
    public Data(Object data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
