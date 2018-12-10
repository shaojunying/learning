import heapq


def main():
    result = compute_the_path_of_TSP_problem()
    print(result[0])


def compute_the_path_of_TSP_problem():
    """用分支界限法计算TSP问题的路径"""

    # 首先利用贪心算法计算一个问题的上界
    c = [[100, 3, 1, 5, 8],
         [3, 100, 6, 7, 9],
         [1, 6, 100, 4, 2],
         [5, 7, 4, 100, 3],
         [8, 9, 2, 3, 100]]

    # 首先计算出整体的上下界
    upper_bound = compute_upper_bound(c)
    lower_bound = compute_lower_bound(c)
    # 接下来用分支界限法计算最优解

    # 此变量中子元素是元组,元组的内容是
    # ([已经走过的点集合],走过的路径总代价,当前走法对应的代价的下界)
    computed_path_info = [([0], 0, lower_bound)]
    while True:
        # 首先找到代价下界最小的元素,之后遍历所有没有走过的节点,都压入数组中

        # 找出computed_path_info中代价下界最小的元素,并从数组中取出
        cur_node_index = computed_path_info.index(min(computed_path_info, key=lambda x: x[2]))
        cur_node = computed_path_info.pop(cur_node_index)
        # 如果当前找到的代价最小的元素已经走过了所有路径,那么该路径就是要求的路径,直接返回即可
        if len(cur_node[0]) == len(c):
            return cur_node
        # 此处声明一个变量,记录在cur_node中添加一个当前所在节点对应未走过的最小路径相信信息
        for node in range(len(c)):
            if node in cur_node[0]:
                # 说明i是已经在路径中了,就不用走了
                continue
            temp_visited_path = cur_node[0] + [node]
            path_info = compute_the_path_traveled_path_info(c=c, temp_visited_path=temp_visited_path,
                                                            cur_node=cur_node)
            if path_info[2] <= upper_bound:
                computed_path_info.append(path_info)


def compute_upper_bound(c):
    """贪心算法计算上界 顺着图走一遍，每次都选代价最小的那一条"""
    path = [0]
    upper_bound = 0
    for _ in range(len(c)):
        if len(path) == len(c):
            upper_bound += c[0][path[-1]]
            path.append(0)
            break
        min_index = -1
        min_value = 1000

        for j in range(len(c)):
            if j not in path and c[path[-1]][j] < min_value:
                min_index = j
                min_value = c[path[-1]][j]
        path.append(min_index)
        upper_bound += min_value
    return upper_bound


def compute_lower_bound(c):
    """取每个节点的两个最小代价的路径计算整体的下界"""
    lower_bound = 0
    # 接下来用每个顶点出发的最短的两条边计算问题的下界
    for i in range(len(c)):
        for j in heapq.nsmallest(2, c[i]):
            lower_bound += j
    return int(lower_bound / 2)


def compute_the_path_traveled_path_info(c, temp_visited_path, cur_node):
    """计算当前走过的路径对应的信息"""
    # 此处需要分别计算出走过路径的总代价,当前走法对应的代价的下界
    cost_of_visited_road = cur_node[1] + c[cur_node[0][-1]][temp_visited_path[-1]]
    # 当前的下界为(2*已经走过的路径+走过的节点上除了走过路径的最小的一个节点+未走过的最小的两个节点)/2
    cur_lower_bound = cost_of_visited_road * 2
    for m in range(len(c)):
        if m == temp_visited_path[0] or m == temp_visited_path[-1]:
            # 说明当前节点已经走过,取出当前行中除了走过路径的最小路径
            two_min_value = heapq.nsmallest(2, c[m])
            two_min_index = [c[m].index(j) for j in two_min_value]
            index = temp_visited_path.index(m)
            if index < len(temp_visited_path) - 1 and two_min_index[0] == temp_visited_path[(index + 1)]:
                cur_lower_bound += c[m][two_min_index[1]]
            else:
                cur_lower_bound += c[m][two_min_index[0]]
        elif m not in temp_visited_path:
            # 当前节点没有走过
            two_min_value = heapq.nsmallest(2, c[m])
            cur_lower_bound += two_min_value[0]
            cur_lower_bound += two_min_value[1]
    cur_lower_bound = (cur_lower_bound + 1) // 2
    return temp_visited_path, cost_of_visited_road, cur_lower_bound


if __name__ == '__main__':
    main()
