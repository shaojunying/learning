import java.util.*;

/**
 * Created by shao on 2018/11/1.
 */
public class maze {

    static int[][] maze = {
            {0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,1,1,1,0,1,0},
            {0,1,1,0,1,1,1,0,1,0},
            {0,1,1,1,1,0,0,1,1,0},
            {0,1,0,0,0,1,1,1,1,0},
            {0,1,1,1,0,1,1,1,1,0},
            {0,1,0,1,1,1,0,1,1,0},
            {0,1,0,0,0,1,0,0,1,0},
            {0,0,1,1,1,1,1,1,1,0},
            {0,0,0,0,0,0,0,0,0,0}};
    private static int[] start = {1,1};
    private static int end[] = {8,8};
    private static Stack<int[]> reversePath=new Stack<>();
    public static void main(String[] args) {
        maze(start);
        Stack<int[]> path = new Stack<>();
        while (!reversePath.empty()){
            path.push(reversePath.pop());
        }

        while (!path.empty()){
            System.out.print(Arrays.toString(path.pop())+", ");
        }
        System.out.println();
    }

    private static boolean canBeAccess(int[] node){
        return node[0]<maze.length&&node[1]<maze.length
                &&maze[node[0]][node[1]]==1;
    }

    private static boolean maze(int[] start){
        reversePath.push(new int[]{start[0],start[1]});
        maze[start[0]][start[1]] = 2;
//        判断是不是到达终点
        if (start[0]==end[0]&&start[1]==end[1]){
            return true;
        }
//        右
        int temp[] = {start[0]+1,start[1]};
        if (canBeAccess(temp)){
            if (maze(temp))
                return true;
        }
//        左
        temp[0]-=2;
        if (canBeAccess(temp)){
            if (maze(temp))
                return true;
        }
//        下
        temp[0]++;
        temp[1]++;
        if (canBeAccess(temp)){
            if (maze(temp))
                return true;
        }
//        上
        temp[1]-=2;
        if (canBeAccess(temp)){
            if (maze(temp))
                return true;
        }
        reversePath.pop();
        return false;
    }

}
