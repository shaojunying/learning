import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by shao on 2018/11/1.
 */
public class RoadColoringProblem {

    static int[][] graph = {{},{0},{0,1},{1},{1,2,3}};
    static int[] color = new int[graph.length];

    public static void main(String[] args) {
        for (int i = 0; i < color.length; i++) {
            color[i]=-1;
        }
        coloring(0,0 );
    }
    static void coloring(int i,int color_index){
        if (i==graph.length){
            System.out.println(Arrays.toString(color));
            return;
        }
        if (color_index>=3){
            return;
        }

        /*给当前节点上色*/
        color[i] = color_index;
        /*判断当前节点的颜色是否跟别的节点有重复*/
        boolean hasError = false;
        if (graph[i].length!=0) {
            for (int j = 0; j < graph[i].length; j++) {
                if (color[i] == color[graph[i][j]]) {
                    hasError = true;
                    break;
                }
            }
        }
        /*当前颜色和别的颜色重复
        * 换颜色*/
        if (hasError){

        }else {
            /*当前颜色满足,继续向下进行*/
            coloring(i+1, 0);
        }
        /*对第0号元素上每种颜色分别输出*/
        coloring(i,color_index+1 );
    }
}
