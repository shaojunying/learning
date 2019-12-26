import numpy as np


def compute_loss(cur_w, cur_b, x, y):
    """
    根据当前 w,b,x,y计算loss
    :param cur_w:
    :param cur_b:
    :param x:
    :param y:
    :return: loss
    """
    loss = 0
    data_len = len(x)
    for i in range(data_len):
        loss += 1 / data_len * (cur_w * x[i] + cur_b - y[i]) ** 2
    return loss


def compute_gradient(cur_w, cur_b, x, y):
    """
    计算当前梯度
    :param cur_w:
    :param cur_b:
    :param x:
    :param y:
    :return: w,b梯度
    """
    gradient_w = 0
    gradient_b = 0

    data_len = len(x)
    for i in range(data_len):
        gradient_w += 2 / data_len * x[i] * (cur_w * x[i] + cur_b - y[i])
        gradient_b += 2 / data_len * (cur_w * x[i] + cur_b - y[i])

    return gradient_w, gradient_b


def gradient_descent(init_w, init_b, x, y, learning_rate=0.005, num_iterations=10000):
    """
    循环进行梯度下降
    :param init_w:
    :param init_b:
    :param x:
    :param y:
    :param learning_rate:
    :param num_iterations:
    :return:
    """
    cur_w = init_w
    cur_b = init_b
    for i in range(num_iterations):
        gradient_w, gradient_b = compute_gradient(cur_w, cur_b, x, y)
        loss = compute_loss(cur_w, cur_b, x, y)
        if i % 10 == 0:
            print("step:", i, "loss:", loss, "w:", cur_w, "b:", cur_b)
        cur_w -= learning_rate * gradient_w
        cur_b -= learning_rate * gradient_b


if __name__ == '__main__':
    data = np.loadtxt("data.csv")
    x, y = np.split(data, [1], 1)
    x = x.reshape(x.shape[0])
    y = y.reshape(y.shape[0])

    init_w = 0
    init_b = 0
    gradient_descent(init_w, init_b, x, y)
    # print(loss)
