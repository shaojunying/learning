import random
from copy import copy

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
    j = end

    index = random.randint(start, end)
    nums[index], nums[start] = nums[start], nums[index]
    while True:
        while i < end and nums[i] < nums[start]:
            i += 1
        while j > start and nums[j] > nums[start]:
            j -= 1
        if i >= j:
            break
        nums[i], nums[j] = nums[j], nums[i]
        i += 1
        j -= 1
    # 此时i指向第一个大于等于num[start]的元素,j指向第一个小于等于的元素
    nums[start], nums[j] = nums[j], nums[start]
    # i指向(第一个大于)或者等于的元素,j指向一个等于的元素
    if j <= k - 1 < i:
        return nums[k - 1]
    elif i <= k - 1:
        # 在右边
        return helper(nums, i, end)
    elif j > k - 1:
        # 在左边
        return helper(nums, start, j - 1)


k = 1000
n = 2000

# 此处要考虑将数组全部赋为相同值和1,2,3,4的情况
nums = [random.randint(0, n) for _ in range(n)]
nums1 = copy(nums)
find_num = helper(nums1, 0, n - 1)
print("要寻找的第", k, "小的元素的下标是", nums.index(find_num), "该元素的值是", find_num)
