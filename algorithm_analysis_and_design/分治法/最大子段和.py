import random
import sys


# 此处[start,end]
def helper(nums, start, end):
    if start == end:
        return nums[start]
    # 分别计算中线两边的子段和,以及从中点开始的字段和
    mid = (start + end) // 2
    # 求左子段和
    left_num = helper(nums, start, mid)
    # 求右子段和
    right_num = helper(nums, mid + 1, end)

    mid_num = nums[mid]
    temp = 0
    for i in range(mid, start - 1, -1):
        temp += nums[i]
        if temp > mid_num:
            mid_num = temp
    temp = mid_num
    for i in range(end, mid, -1):
        temp += nums[i]
        if temp > mid_num:
            mid_num = temp
    return max(left_num, right_num, mid_num)


def main():
    nums = [random.randint(-100, 100) for _ in range(100)]
    print(nums)
    print(helper(nums, 0, 100 - 1))


if __name__ == '__main__':
    main()
