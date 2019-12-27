from functools import cmp_to_key
from typing import List, Any, Union

horse_directions = [[2, -1], [-1, 2],
                    [-2, 1], [1, -2],
                    [-1, -2], [-2, -1],
                    [1, 2], [2, 1]]

n = 6

visited = [[0] * n] * n

path = []


def computeSteps(p, q):
    step = 0
    for horseDirection in horse_directions:
        p1 = p + horseDirection[0]
        q1 = q + horseDirection[1]
        if -1 < p1 < n and -1 < q1 < n and not visited[p1][q1]:
            step += 1
    return step



def helper(node):
    print(node[1])
    visited[node[0]][node[1]] = True
    path.append(node)

    if len(path) == n * n:
        return True

    temp: List[List[Union[Union[List[Union[int, Any]], int], Any]]] = []

    for horse_direction in horse_directions:
        p = node[0] + horse_direction[0]
        q = node[1] + horse_direction[1]
        if -1 < p < n and -1 < q < n and not visited[p][q]:
            temp.append([[p, q], computeSteps(p, q)])

    temp.sort(key=)


helper([4, 4])
