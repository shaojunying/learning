x = [6, 7, 12, 14]
t = [5, 6, 5, 1]
m = 20
# e[i]表示下标j大于i,且距x[i]大于5英里中距x[i]最近的点
e = [-1] * len(x)
for i in range(len(e)):
    for j in range(i + 1, len(e)):
        if x[j] - x[i] > 5:
            e[i] = j
            break
print(e)
"""
f(i)代表在从xi开始向后放广告牌的最大收益
f(i) = max(f(e[i])+t[i],f(i+1))
"""
memo = [-1] * len(x)
memo[-1] = t[-1]
for i in range(len(memo) - 2, -1, -1):
    memo[i] = memo[i + 1]
    if e[i] == -1:
        memo[i] = max(memo[i], t[i])
    else:
        memo[i] = max(memo[i], memo[e[i]] + t[i])
print(memo)
"""
找路线
"""
selected_items = []
i = 0
while i < len(memo) - 1:
    if memo[i] > memo[i + 1]:
        # 此时说明i被选上了
        selected_items.append(i)
        if e[i] == -1:
            break
        i = e[i]
    else:
        i += 1
    print(i)
print(selected_items)
