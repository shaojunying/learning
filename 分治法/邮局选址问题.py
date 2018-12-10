import random

n = 100
positions = [(random.randint(0, n), random.randint(0, n)) for x in range(n)]
# 首先将position的x,y坐标分别排序
"""通过类似快速排序找到中位数"""
mid = n // 2
"""转化为找数组中第mid大的节点"""


# [start,end]
def helper(nums, start, end):
    i = start + 1
    j = end
    while True:
        while i <= end and nums[i] < nums[start]:
            i += 1
        while j >= start and nums[j] > nums[start]:
            j -= 1
        if i >= j:
            break
        nums[i], nums[j] = nums[j], nums[i]
    # 这里i指向第一个大于的元素,j指向第一个小于的元素
    if j > 0:
        nums[start], nums[j] = nums[j], nums[start]



print(positions)
