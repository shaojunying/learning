import random

"""
找到n个元素中第k小的元素的值和它的位置
小于的放左边,大于的放右边
可以尝试三路快排
"""


# 这里范围是[start,end]
def helper(nums, start, end):
    # 这里将nums[start]作为基准元素
    i = start + 1
    # 这里取得最后一个元素的下标
    j = end - 1
    while True:
        while i < end and nums[i] < nums[start]:
            i += 1
        while j > 0 and nums[j] > nums[start]:
            j -= 1
        if i >= j:
            break
        nums[i], nums[j] = nums[j], nums[i]
    # nums[start],nums[]
    # 此时i指向第一个大于num[start]的元素,j指向第一个小于的元素

    # 这里可能会越界
    nums[start], nums[j] = nums[j], nums[start]
    if i < k:
        # 在右边
        return helper(nums, i, end)
    elif j > k:
        # 在左边
        return helper(nums, start, j + 1)
    else:
        print(nums)
        return j


k = 10
n = 100

# 此处要考虑将数组全部赋为相同值和1,2,3,4的情况
nums = [0 for _ in range(n)]
print(nums)
print(helper(nums, 0, n-1))
