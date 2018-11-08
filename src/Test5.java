public class Test5 {
    static final int MAX = 1000;
    static int d[][] = { {0,0,0}, {0,0,0}, {0,0,-1}, {0,-1,0}, {0,0,1}, {0,1,0} };
    private static int [][] curr_guard =new int[MAX][MAX];
    private static int [][] curr_monitor =new int[MAX][MAX];
    private static int [][] best_curr_guard =new int[MAX][MAX];   //x用来设置当前警卫，y用来表示监控情况，bestx返回最终结果
    private static int n, m, min_guard_count, curr_guard_count = 0, showroom_monitored_count = 0;   //当前已设置的警卫数为k，受监视的陈列室数为t，当前最少警卫数为best
    private static int t1, t2, more;               //判断下界剪枝的条件参数
    boolean p;

    /**
     * 世界名画陈列馆问题（回溯法）
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        m = 10;
        n = 10;
        compute(); //计算
        System.out.println("最少需要"+ min_guard_count +"个警卫！");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++)
                System.out.print( best_curr_guard[i][j]+" ");
            System.out.println();
        }

    }
    //在(i, j)处设置一个警卫，并改变其周围受监控情况
    static void putAGuard(int i, int j) {
        curr_guard[i][j] = 1;
        curr_guard_count++;
        for (int r = 1; r <= 5; r++) {    //在自己本身跟上下左右五个地方设置受控
            int p = i + d[r][1];
            int q = j + d[r][2];
            curr_monitor[p][q]++;
            if (curr_monitor[p][q] == 1)
                showroom_monitored_count++;
        }
    }
    //撤销在(i, j)处设置的警卫，并改变其周围受监控情况
    static void cancelAGuard(int i, int j) {
        curr_guard[i][j] = 0;
        curr_guard_count--;
        for (int r = 1; r <= 5; r++) {
            int p = i + d[r][1];
            int q = j + d[r][2];
            curr_monitor[p][q]--;
            if (curr_monitor[p][q] == 0)
                showroom_monitored_count--;
        }
    }
    //回溯搜索
    static void search(int i, int j) {

        //从上到下，从左至右搜索没被监控的位置
        do {
            j++;
            if (j > m) {
                i++;
                j = 1;
            }
        } while (!((curr_monitor[i][j] == 0) || (i > n)));


        /*
        * i>n说明全部被监控了
        * 更新最小警卫数
        * 记录每个警卫的位置
         * */
        if (i > n) {
            if (curr_guard_count < min_guard_count) {            //刷新警卫值
                min_guard_count = curr_guard_count;
                for (int p = 1; p <= n; p++)
                    for (int q = 1; q <= m; q++)
                        best_curr_guard[p][q] = curr_guard[p][q];
                return;
            }
        }

        /*因为一个警卫最多监视5个，所以如果当前警卫数量加上没有被监视的屋子/5大于最小警卫数，就不用做进一步的检测*/
        if (curr_guard_count + (t1 - showroom_monitored_count)/5 >= min_guard_count)    return;
        if ((i < n - 1) && (curr_guard_count + (t2 - showroom_monitored_count)/5 >= min_guard_count))    return;


        /*
        * 在(i,j+1),(i,j+2)都已经被监控的情况下
        * 三个点的遍历顺序应该是(i+1,j),(i,j+1),(i,j)
        * */

        /*i<n说明当前节点没有被监控到*/
        if (i < n) {
            /*在当前节点的右边添加一个警卫*/
            putAGuard(i + 1, j);
            //继续调用当前函数,定位到下一个没有被监控的函数
            search(i, j);
            /*取消当前节点的警卫*/
            cancelAGuard(i + 1,j);
        }
        /*取消了当前节点的右节点的监控,如果当前节点的下节点没有被监控，就监控当前节点*/
        if (curr_monitor[i][j + 1] == 0) {
            putAGuard(i, j);
            search(i, j);
            cancelAGuard(i, j);
        }

        /*没有到最后一个元素*/
        if ((j < m) && ((curr_monitor[i][j + 1] == 0) || (curr_monitor[i][j + 2] == 0))) {    //结点r
            putAGuard(i, j + 1);
            search(i, j);
            cancelAGuard(i, j + 1);
        }
    }

    static void compute() {



        more = m/4 + 1;
        if (m % 4 == 3)
            more++;
        else if (m % 4 == 2)
            more += 2;
        t2 = m * n + more + 4;
        t1 = m * n + 4;
//        将best置为最大值
        min_guard_count = 65536;


//        1*1的情况直接输出结果
        if (m == 1 && n == 1) {
            System.out.println(1);
            System.out.println(1);
        }

        //在整个外面加上一圈，便于处理边界情况
        for (int i = 0; i <= m + 1; i++) {
            curr_monitor[0][i] = 1;
            curr_monitor[n + 1][i] = 1;
        }
        for (int i = 0; i <= n + 1; i++) {
            curr_monitor[i][0] = 1;
            curr_monitor[i][m + 1] = 1;
        }
        search(1, 0);
    }


}
