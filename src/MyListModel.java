import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by shao on 2019/5/18 12:29.
 */
public class MyListModel extends AbstractListModel {
    private List<String> idList = new LinkedList<>();
    private List<Integer> portList = new LinkedList<>();

    public MyListModel() {
    }

    public void setPort2IdMap(Map<Integer, String> port2IdMap) {
        idList = new LinkedList<>();
        portList = new LinkedList<>();
        port2IdMap.forEach((port, id) -> {
            idList.add(id);
            portList.add(port);
        });
    }

    public int getPortAt(int index) {
        return portList.get(index);
    }


    @Override
    public Object getElementAt(int index) {
        return idList.get(index);
    }

    @Override
    public int getSize() {
        return idList.size();
    }
}
