import copy


def main():
    compute_8_digit_problem()


def compute_8_digit_problem():
    start_state = [[1, -1, 3],
                   [4, 2, 6],
                   [7, 5, 8]]

    """
    此处声明一个记录走过的所有路径的信息
    [当前9个点中的数值,当前可移动点的横纵坐标,当前的代价]
    """
    computed_path_info = [[start_state, (0, 1), compute_the_price_of_a_path(start_state), []]]
    # 可移动的四个方向
    directions = [[-1, 0], [1, 0], [0, 1], [0, -1]]

    while True:
        cur_min_index = computed_path_info.index(min(computed_path_info, key=lambda x: x[2]))
        cur_node = computed_path_info.pop(cur_min_index)
        if cur_node[2] == 0:
            # 说明当前节点已经是正确的节点
            print(cur_node)
            break
        # 遍历四个方向
        for index, direction in enumerate(directions):
            temp_visited_path = copy.deepcopy(cur_node)
            new_x = temp_visited_path[1][0] + direction[0]
            new_y = temp_visited_path[1][1] + direction[1]
            if new_x < 0 or new_x >= len(start_state) or new_y < 0 or new_y >= len(start_state):
                continue
            # 交换两个点的值
            temp_visited_path[0][temp_visited_path[1][0]][temp_visited_path[1][1]], \
            temp_visited_path[0][new_x][new_y] \
                = temp_visited_path[0][new_x][new_y], \
                  temp_visited_path[0][temp_visited_path[1][0]][temp_visited_path[1][1]]
            temp_visited_path[1] = (new_x, new_y)
            temp_visited_path[2] = compute_the_price_of_a_path(temp_visited_path[0])
            temp_visited_path[3].append(index)
            computed_path_info.append(temp_visited_path)
        # print(computed_path_info)


def compute_the_price_of_a_path(state):
    """计算当前路径的代价函数"""
    error_count = 0
    for i, list in enumerate(state):
        for j, value in enumerate(list):
            # print(i*3+j+1,value)
            if value != -1 and i * 3 + j + 1 != value:
                # 说明当前元素错位
                error_count += 1
    return error_count


if __name__ == '__main__':
    main()
