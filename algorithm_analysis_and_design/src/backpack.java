import java.util.LinkedList;
import java.util.List;

/**
 * Created by shao on 2018/10/18.
 */
public class backpack {

    public final static int max_space = 10;
    public static int max_value = 0;
    public static int cur_space = 0;
    public static int cur_value = 0;
    public static int[][] goods = {{7,42},{3,12},{4,40},{5,25}};

    public static void main(String[] args) {

        getValue(0);

        System.out.println(max_value);
    }

    private static void getValue(int i) {
        if (i>=goods.length){
            max_value = Math.max(cur_value,max_value );
            return;
        }


        if (cur_space+goods[i][0]<=max_space){
            cur_space+=goods[i][0];
            cur_value+=goods[i][1];
            getValue(i+1);
            cur_space-=goods[i][0];
            cur_value-=goods[i][1];
        }

        getValue(i+1);
    }


}
