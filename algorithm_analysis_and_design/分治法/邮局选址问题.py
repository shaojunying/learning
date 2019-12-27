import random


def helper(nums, start, end):
    """
    [start,end]
    :param nums:
    :param start:
    :param end:
    :return:
    """
    if start >= end:
        return
    # 此处一定要找一个随机数,不然可能会一直卡死,无限调用递归
    index = random.randint(start, end)
    nums[start], nums[index] = nums[index], nums[start]
    i = start + 1
    j = end
    while True:
        # 这里不能有=,如果有=,遇到连续相同的元素,会被归到一端,如果没有=将会平分到两端
        while i < end and nums[i] < nums[start]:
            i += 1
        while j > start and nums[j] > nums[start]:
            j -= 1
        if i >= j:
            break
        nums[i], nums[j] = nums[j], nums[i]
        i += 1
        j -= 1
    # 这里i指向第一个大于的元素,j指向第一个小于的元素
    nums[start], nums[j] = nums[j], nums[start]
    # 交换之后i指向第一个大于的元素,j指向第一个等于的元素
    helper(nums, start, j - 1)
    helper(nums, i, end)


def quick_sort(nums):
    """
    快速排序
    :param nums:
    :return:
    """
    helper(nums, 0, len(nums) - 1)


def main():
    n = 100
    positions = [(random.randint(0, n), random.randint(0, n)) for x in range(n)]
    # 首先将position的x,y坐标分别排序
    """通过类似快速排序找到中位数"""
    mid = n // 2
    x_list, y_list = list(zip(*positions))
    x_list = list(x_list)
    y_list = list(y_list)
    quick_sort(x_list)
    quick_sort(y_list)
    print(positions)
    print("邮局应该选择的位置是: (", x_list[mid], ",", y_list[mid], ")")


if __name__ == '__main__':
    main()
