import javax.swing.text.html.parser.Entity;
import java.util.*;

public class horseWalk {

    private int[][] horseDirections = {
            {2,-1},{-1,2},
            {2,1},{1,2},
            {-1,-2},{-2,-1},
            {1,-2},{-2,1}};

    private int n = 6;

    private boolean[][] visited = new boolean[n][n];

    Stack<int[]> path = new Stack<>();

    private horseWalk(){
        int[] start = {0,0};
        horseWalkHelper(start);
        for (int[] aPath : path) {
            System.out.print(String.valueOf(aPath[0]*n+aPath[1]+1) + ",");
        }
        System.out.println();
    }

    private int computeSteps(int[] node){
        int step = 0;
        for (int[] horseDirection : horseDirections) {
            int p = node[0]+horseDirection[0];
            int q = node[1]+horseDirection[1];
            if (p>=0&&p<visited.length&&q>=0&&q<visited.length&&!visited[p][q]){
                step++;
            }
        }
        return step;
    }

    private boolean horseWalkHelper(int[] node){
        visited[node[0]][node[1]]=true;
        path.addAll(Collections.singleton(node));

        if (path.size()==visited.length*visited.length){
            System.out.println("成功");
            return true;
        }

        for (int[] horseDirection : horseDirections) {
            int p = node[0]+horseDirection[0];
            int q = node[1]+horseDirection[1];

//            在这里记录每个点接下来的可走路径,每次找下次可走路径最少的那一条


            if (p>=0&&p<visited.length&&q>=0&&q<visited.length&&!visited[p][q]){
                if (horseWalkHelper(new int[]{p,q})){
                    return true;
                }
            }




        }
        int[] a = path.pop();
        visited[a[0]][a[1]] = false;
        return false;
    }


    public static void main(String[] args) {
        new horseWalk();
    }

}
