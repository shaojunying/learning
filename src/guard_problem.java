import java.util.Arrays;

/**
 * Created by shao on 2018/11/8.
 */
public class guard_problem {

    static int m = 2;
    static int n = 2;

    static int guard[][] = new int[m][n];


    static int helperInt[][] = new int[][]{
            {-1,0},{1,0},{0,-1},{0,1}
    };


    static int helperInt2[][] = new int[][]{
            {0,0},{-1,0},{1,0},{0,-1},{0,1}
    };


    public static void main(String[] args) {
        helper(new int[]{0,0});
    }

    /*
    * 首先给0,0  0,1 1,0三个节点中的一个放警卫
    * 之后找下一个没有被警卫覆盖的点,给他在他周围的点防止一个警卫
    * 一直到所有点都被覆盖
    * 记录当前警卫数
    * 返回,找下一个防止警卫的地方
    * */

    static void putGuard(int[] nums){
        guard[nums[0]][nums[1]] = -1;

//        周围的节点都需要设置为已经被守卫
        for (int[] ints : helperInt) {
            int p = nums[0]+ints[0];
            int q = nums[1]+ints[1];
            if (p>-1&&p<m&&q>-1&&q<n&&guard[p][q]!=-1){
                guard[p][q] ++;
            }
        }
    }
    static void cancelGuard(int[] nums){
        guard[nums[0]][nums[1]] = 0;

        for (int[] ints : helperInt) {
            int p = nums[0]+ints[0];
            int q = nums[1]+ints[1];
            if (p>-1&&p<m&&q>-1&&q<n&&guard[p][q] != -1){
                guard[p][q]--;
            }
        }

    }

    static int[] findNextPoint(){

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (guard[i][j] == 0){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

//    代表我们需要给当前nums对应的方框寻找一个警卫
    private static void helper(int[] nums) {
/*说明当前nums对应的位置需要警卫,需要从周围5个点中选择一个放上警卫*/


        for (int[] ints : helperInt2) {
            int p = nums[0]+ints[0];
            int q = nums[1]+ints[1];
            if (p>-1&&p<m&&q>-1&&q<n){
//                给当前p,q添加一个警力
                putGuard(new int[]{p,q});

                int[] temp = findNextPoint();
                if (temp != null){
                    helper(temp);
                }
                System.out.println(Arrays.deepToString(guard));
                cancelGuard(new int[]{p,q});
            }
        }

    }

}
