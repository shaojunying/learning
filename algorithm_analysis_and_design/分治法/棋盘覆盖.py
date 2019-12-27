import random

fill_num = 0


def helper(point, x, y, size, fill_table):
    global fill_num
    if size == 1:
        return
    fill_num += 1
    cur_fill_num = fill_num
    # 首先计算出中点
    size //= 2

    # 左上角
    if point[0] < x + size and point[1] < y + size:
        # 特殊点在左上角
        helper(point, x, y, size, fill_table)
    else:
        # 特殊点不在左上角
        fill_table[x + size - 1][y + size - 1] = cur_fill_num
        # 将图上色的那一块看作特殊块
        helper((x + size - 1, y + size - 1), x, y, size, fill_table)

    # 左下角
    if point[0] < x + size and point[1] > y + size - 1:
        # 特殊点在左上角
        helper(point, x, y + size, size, fill_table)
    else:
        # 特殊点不在左上角
        fill_table[x + size - 1][y + size] = cur_fill_num
        # 将图上色的那一块看作特殊块
        helper((x + size - 1, y + size), x, y + size, size, fill_table)
    # 右上角
    if point[0] > x + size - 1 and point[1] < y + size:
        # 特殊点在左上角
        helper(point, x + size, y, size, fill_table)
    else:
        # 特殊点不在左上角
        fill_table[x + size][y + size - 1] = cur_fill_num
        # 将图上色的那一块看作特殊块
        helper((x + size, y + size - 1), x + size, y, size, fill_table)
    # 右下角
    if point[0] > x + size - 1 and point[1] > y + size - 1:
        # 特殊点在左上角
        helper(point, x + size, y + size, size, fill_table)
    else:
        # 特殊点不在左上角
        fill_table[x + size][y + size] = cur_fill_num
        # 将图上色的那一块看作特殊块
        helper((x + size, y + size), x + size, y + size, size, fill_table)


def main():
    # 棋盘大小
    n = 2 ** 4
    # 特殊点坐标
    point = (random.randint(0, n - 1), random.randint(0, n - 1))
    fill_table = [[0] * n for _ in range(n)]
    fill_table[point[0]][point[1]] = -1
    helper(point, 0, 0, n, fill_table)
    for i in fill_table:
        for j in i:
            print("%3d" % j, end=" ")
        print()


if __name__ == '__main__':
    main()
