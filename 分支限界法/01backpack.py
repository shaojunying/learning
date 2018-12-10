def main():
    result = compute_the_01backpack_problem()
    print("物品的放与不放的情况是", result[0])
    print("当前使用的总体积是", result[1])
    print("放入物品的总价值是", result[2])


def compute_the_01backpack_problem():
    goods = [[4, 40], [7, 42], [5, 25], [3, 12]]
    max_volume = 10
    # 对物品按照单位体积的价值进行降序排列
    goods = sorted(goods, key=lambda a: a[1] / a[0], reverse=True)

    """
    首先将不装第一个物品的情况放入
    之后首先计算放入第一个物品的各项信息
    如果放入后总体积不大于最大体积,就将该情况放入,否则不放入
    """

    # computed_path_info [[装入的物品序号],总体积,总价值,当前期望最大价值]
    computed_path_info = [[[0], 0, 0, goods[1][1] / goods[1][0] * max_volume]]
    the_info_when_put_the_first_goods = [[1], goods[0][0], goods[0][1],
                                         goods[1][1] / goods[1][0] * (max_volume - goods[0][0]) + goods[0][1]]
    if the_info_when_put_the_first_goods[1] <= max_volume:
        computed_path_info.append(the_info_when_put_the_first_goods)

    """每次找出队列中上界最大的元素,对它考虑它后一个元素的放与不放,分别判断是否加入到队列中"""

    while True:
        # 找出当前已经计算的路径中上界最大的路径
        max_value_index = computed_path_info.index(max(computed_path_info, key=lambda x: x[1]))
        cur_node = computed_path_info.pop(max_value_index)

        # 找到了最优解,直接输出即可
        if len(cur_node[0]) == len(goods):
            # 说明已经到达叶节点
            return cur_node

        # 首先计算不装当前物品的情况
        cur_good_value_every_volume = goods[len(cur_node[0])][1] / goods[len(cur_node[0])][0]
        if len(cur_node[0]) < len(goods) - 1:
            next_good_value_every_volume = goods[len(cur_node[0]) + 1][1] / goods[len(cur_node[0]) + 1][0]
        else:
            next_good_value_every_volume = 0
        cur_node_max_value = cur_node[3] - (max_volume - cur_node[1]) * \
                             (cur_good_value_every_volume - next_good_value_every_volume)
        computed_path_info.append([cur_node[0] + [0], cur_node[1], cur_node[2], cur_node_max_value])

        # 计算装当前物品的情况
        if cur_node[1] + goods[len(cur_node[0])][0] <= max_volume:
            # 加入当前节点没有越界
            cur_node_max_value = cur_node[3] \
                                 - cur_good_value_every_volume * (max_volume - cur_node[1]) \
                                 + goods[len(cur_node[0])][1] \
                                 + next_good_value_every_volume * (
                                         max_volume - cur_node[1] - goods[len(cur_node[0])][0])
            computed_path_info.append([cur_node[0] + [1],
                                       cur_node[1] + goods[len(cur_node[0])][0],
                                       cur_node[2] + goods[len(cur_node[0])][1],
                                       cur_node_max_value])


if __name__ == '__main__':
    main()
