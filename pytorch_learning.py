import numpy as np
import torch

# 从numpy生成数据
data = np.ones([2, 3])
print(torch.from_numpy(data))

# 直接将list转为tensor
print(torch.tensor([2, 3]))

# 生成指定shape tensor
print(torch.Tensor(2, 3))
