goods = [[4, 40], [7, 42], [5, 25], [3, 12]]
max_volume = 10
"""
状态是
f(m,n)指背包容量为n,装前m个物品达到的最大价值
f(m,n) = max(f(m-1,n),f(m-1,n-goods[m][0])+goods[m][1])
"""

m = len(goods)
memo = [[0] * (max_volume + 1) for _ in range(m)]

for i in range(max_volume + 1):
    if i >= goods[0][0]:
        memo[0][i] = goods[0][1]
for i in range(1, m):
    for j in range(max_volume + 1):
        memo[i][j] = memo[i - 1][j]
        cur_goods = goods[i]
        if cur_goods[0] >= j:
            continue
        memo[i][j] = max(memo[i][j], memo[i - 1][j - cur_goods[0]] + cur_goods[1])
print("最大价值是", memo[-1][-1])
selected_items = []
i = max_volume
for index in range(len(memo) - 1, 0, -1):
    if memo[index][i] == memo[index - 1][i]:
        # 说明index元素没有被选取
        pass
    else:
        selected_items.append(index)
        i = goods[index][0]
if i != 0:
    selected_items.append(0)
print("选择的元素序号是", list(reversed(selected_items)))
