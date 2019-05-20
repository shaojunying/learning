import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by shao on 2019/5/18 11:33.
 */
public class test {
    public static void main(String[] args) {
        // 给frame换一个样式
//        JFrame.setDefaultLookAndFeelDecorated(true);
//
//        JFrame frame = new JFrame("test");
//        Container container = frame.getContentPane();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        String[] words= { "quick", "brown", "hungry", "wild"};
//        JList<String> stringJList = new JList<>(words);
//        stringJList.setBounds(10, 10, 390, 500);
//        container.add(stringJList);
//        JButton button = new JButton("shao");
//        button.setBounds(410, 450, 100, 30);
//        container.add(button);
//
//
//        // 显示窗口
//        frame.setSize(600, 600);
//        frame.setLocationRelativeTo(null);
//        frame.setLayout(null);
//        frame.setVisible(true);

        Map<Integer, LinkedList<String>> map = new HashMap<>();
        map.put(1, new LinkedList<>());
        List<String> stringList = map.get(1);
        stringList.add("111");
        System.out.println(map.get(1));

    }
}
