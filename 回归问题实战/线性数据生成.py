import numpy as np
from matplotlib import pyplot as plt

np.set_printoptions(suppress=True)


def generate_data(quantity=1000):
    """
    生成二维随机数据
    :param quantity: 要生成数据的数量
    :return:
    """
    # 从y = w * x + b + 噪声 生成数据
    w = 1
    b = 3

    x = 5 + 10 * np.random.random([quantity])
    noise = np.random.normal(0.01, 1, [quantity])
    y = w * x + b + noise

    x = x.reshape([quantity, 1])
    y = y.reshape([quantity, 1])
    data = np.concatenate([x, y], axis=1)

    return data


def show_data(data):
    """
    展示数据
    :param data:
    :return:
    """
    
    plt.xlabel("x")
    plt.ylabel("y")
    x, y = np.split(data, [1], axis=1)
    plt.plot(x, y, '.')
    plt.show()


if __name__ == '__main__':
    data = generate_data()

    show_data(data)

    np.savetxt("data.csv", data, fmt='%.15f')
