import java.util.Arrays;

/**
 * Created by shao on 2018/11/8.
 */
public class guard_problem {

    private static int m = 10;
    private static int n = 10;

    private static int monitored[][] = new int[m][n];
    private static int guard[][] = new int[m][n];
    private static int bestGuard[][] = new int[m][n];

    /*假设每一个机器人只能看到左中右或者上中下,都是1*3的长条,那么需要n*m/3+1个长条,最后会剩余一个1*1或者1*2或者2*2的方块,补一个就足够
    * 所以初始最小守卫数量是n*m/3+2个*/
    private static int minGuardCount = n*m/3+2;
    private static int curGuardCount = 0;
    private static int curMonitorRoomCount = 0;


    private static int middleRightDown[][] = new int[][]{
            {1,0},{0,0},{0,1}
    };


    private static int upDownLeftRightMiddle[][] = new int[][]{
            {0,0},{-1,0},{1,0},{0,-1},{0,1}
    };


    public static void main(String[] args) {

        /*monitored[i][j]>0表示当前节点被几个警卫监视
        * =0表示当前节点没有被警卫监视*/
        /*monitor[i][j]=1表示当前节点有警卫*/

        helper(new int[]{0,0});
        System.out.println("最少需要"+minGuardCount+"个警卫");
        for (int[] ints : bestGuard) {
            System.out.println(Arrays.toString(ints));
        }
    }

    /*
    * 首先给0,0  0,1 1,0三个节点中的一个放警卫
    * 之后找下一个没有被警卫覆盖的点,给他在他周围的点防止一个警卫
    * 一直到所有点都被覆盖
    * 记录当前警卫数
    * 返回,找下一个防止警卫的地方
    * */

    private static void putGuard(int[] nums){
        guard[nums[0]][nums[1]] = 1;
        curGuardCount++;
//        上下左右中的节点都需要设置为已经被守卫
        for (int[] ints : upDownLeftRightMiddle) {
            int p = nums[0]+ints[0];
            int q = nums[1]+ints[1];
            if (p>-1&&p<m&&q>-1&&q<n){
                monitored[p][q] ++;
                if (monitored[p][q] == 1){
                    curMonitorRoomCount ++;
                }
            }
        }
    }
    private static void cancelGuard(int[] nums){
        guard[nums[0]][nums[1]] = 0;
        curGuardCount--;
        for (int[] ints : upDownLeftRightMiddle) {
            int p = nums[0]+ints[0];
            int q = nums[1]+ints[1];
            if (p>-1&&p<m&&q>-1&&q<n){
                monitored[p][q]--;
                if (monitored[p][q] == 0){
                    curMonitorRoomCount --;
                }
            }
        }

    }

    private static int[] findNextPoint(){

        int i=0;
        int j=0;

        while (true){
            j++;
            if (j>=n){
                j=0;
                i++;
            }
            if (i>=m){
                return null;
            }
            if (monitored[i][j]==0){
                return new int[]{i,j};
            }
        }
    }

//    代表我们需要给当前nums对应的方框寻找一个警卫
    private static void helper(int[] nums) {

        if (curGuardCount+(m*n-curMonitorRoomCount+4)/5 >= minGuardCount){
            return;
        }

/*说明当前nums对应的位置需要警卫,需要从周围5个点中选择一个放上警卫*/
        for (int[] ints : middleRightDown) {
            int p = nums[0]+ints[0];
            int q = nums[1]+ints[1];
            if (p>-1&&p<m&&q>-1&&q<n){
//                给当前p,q添加一个警力
                putGuard(new int[]{p,q});
                /*给当前节点放置警力之后找到下一个没有被警力覆盖的地方*/
                int[] temp = findNextPoint();
                if (temp != null){
                    helper(temp);
                }else {
                    if (curGuardCount < minGuardCount) {
                        for (int i = 0; i < m; i++) {
                            for (int j = 0; j < n; j++) {
                                bestGuard[i][j] = guard[i][j];
                            }
                        }
                        minGuardCount = curGuardCount;
//                        System.out.println("当前警力情况"+ Arrays.deepToString(guard));
//                        System.out.println("当前监视情况"+ Arrays.deepToString(monitored));
                    }
                }
                cancelGuard(new int[]{p,q});
            }
        }

    }

}
