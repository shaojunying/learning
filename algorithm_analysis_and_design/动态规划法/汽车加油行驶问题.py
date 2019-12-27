from sys import *

# 方形网格边长
n = 9
# 加满油之后可以行驶的边数
k = 3
# 加油费用
a = 2
# x,y坐标减少时要付的费用
b = 3
# 增设油库的时候的增设费用
c = 6

table = [[0, 0, 0, 0, 1, 0, 0, 0, 0],
         [0, 0, 0, 1, 0, 1, 1, 0, 0],
         [1, 0, 1, 0, 0, 0, 0, 1, 0],
         [0, 0, 0, 0, 0, 1, 0, 0, 1],
         [1, 0, 0, 1, 0, 0, 1, 0, 0],
         [0, 1, 0, 0, 0, 0, 0, 1, 0],
         [0, 0, 0, 0, 1, 0, 0, 0, 1],
         [1, 0, 0, 1, 0, 0, 0, 1, 0],
         [0, 1, 0, 0, 0, 0, 0, 0, 0]]

directions = [[1, 0, 0], [0, 1, 0], [-1, 0, b], [0, -1, b]]
memo = [[[maxsize, k] for _ in range(n)] for _ in range(n)]
memo[0][0][0] = 0

has_modify = True
while has_modify:
    has_modify = False
    for i in range(n):
        for j in range(n):
            # 遍历每个节点
            min_cost = maxsize
            min_step = -10000
            for direction in directions:
                i1 = i + direction[0]
                j1 = j + direction[1]
                if i1 < 0 or j1 < 0 or i1 > n - 1 or j1 > n - 1:
                    continue
                cost = memo[i1][j1][0]
                step = memo[i1][j1][1] - 1
                # 遇到油库
                if table[i][j] == 1:
                    cost += a
                    step = k
                # 没有遇到油库但是没油了
                if table[i][j] == 0 and step == 0 and (i != n - 1 or j != n - 1):
                    cost += (a + c)
                    step = k
                if min_cost > cost:
                    min_cost = cost
                    min_step = step
            if memo[i][j][0] > min_cost:
                # 说明在后面计算出新的路径,前面的需要再次进行
                has_modify = True
                memo[i][j][0] = min_cost
                memo[i][j][1] = min_step
print(memo[-1][-1][0])
