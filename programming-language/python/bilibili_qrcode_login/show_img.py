"""
show_img
二维码转换为字符串并输出
"""
from PIL import Image

import utils
import show_img


# 在二维码的定位模块（Finder Pattern）中，3×3、5×5、7×7 指的是 模块（Module）的数量，
# 而 像素（Pixel） 是实际图像中的最小单位。它们之间的关系取决于 每个模块由多少像素组成。


def get_cell_size(x1: int = None, y1: int = None,
                  x2: int = None, y2: int = None,
                  img: Image.Image = None) -> tuple:
    """
    ▄▄▄▄▄▄▄▄▄
    █ ▄▄▄▄▄ █
    █ █   █ █
    █ █▄▄▄█ █
    █▄▄▄▄▄▄▄█
    根据定位用图案，获取二维码组成单元的像素大小。注意这里是从定位图案中求解单位模块包含的像素数量。
    :param x1: 左上方 x 轴坐标
    :param y1: 左上方 y 轴坐标
    :param x2: 右下方 x 轴坐标
    :param y2: 右下方 y 轴坐标
    :param img: Image.Image 对象
    :return: (int: 单元像素宽, int: 单元像素高)
    """
    for j in range(y1, y2):
        for i in range(x1, x2):
            pix = img.getpixel((i, j))
            if pix == 255:
                return i - x1, j - y1


def get_cell(img: Image.Image = None) -> tuple:
    """
    获取二维码组成单元的像素大小
    :param img: Image.Image 对象
    :return: (int: 单元像素宽, int: 单元像素高)
    """
    # 位置标识，像素点黑色时 flag=1, 白色时 flag=0
    flag = 0
    # 初始化
    x1 = y1 = 0
    x2 = y2 = 0
    # 获取定位用图案 x 轴坐标区间
    # 按行扫描，找到某一行中第一个黑到白的过渡，记录为 x1（黑开始）和 x2（白结束）
    for j in range(img.height):
        for i in range(img.width):
            pix = img.getpixel((i, j))
            if pix == 0 and flag == 0:
                x1 = i
                flag = 1
            if pix == 255 and flag == 1:
                x2 = i
                flag = 0
                break
    # 获取定位用图案 y 轴坐标区间
    # 按列扫描，找到某一列中第一个黑到白的过渡点，记录为 y1 和 y2
    for i in range(img.width):
        for j in range(img.height):
            pix = img.getpixel((i, j))
            if pix == 0 and flag == 0:
                y1 = j
                flag = 1
            if pix == 255 and flag == 1:
                y2 = j
                flag = 0
                break

    cell_size = get_cell_size(x1=x1, y1=y1, x2=x2, y2=y2, img=img)
    # (10,10)
    return cell_size


def print_qrcode(img: Image.Image = None) -> None:
    """
    二维码以字符串形式输出到控制台
    :param img: Image.Image 对象
    :return: None
    """
    # 输出方块单元
    pixel = ['▄▀ █', '▀▄█ ']
    # 输出颜色反转
    reversal = 1
    # 获取单元像素宽、高
    cell_size = get_cell(img=img)
    # 获取以组成单元为最小单位的二维码高度
    height = int(img.height / cell_size[1])
    # 获取以组成单元为最小单位的二维码宽度
    width = int(img.width / cell_size[0])
    # 待输出字符串
    code = ''

    # 每次同时处理两行
    for y in range(0, height, 2):
        for x in range(width):
            # 列索引不超出高度
            if (y + 1) * cell_size[1] < img.height:
                # 获取当前行当前列方块中心的像素
                pix1 = img.getpixel(
                    (x * cell_size[0] + int(cell_size[0] / 2), y * cell_size[1] + int(cell_size[1] / 2)))
                # 获取下一行当前列方块中心的像素
                pix2 = img.getpixel(
                    (x * cell_size[0] + int(cell_size[0] / 2), (y + 1) * cell_size[1] + int(cell_size[1] / 2)))
                # 根据像素填充输出方向
                # 上黑
                if pix1 == 0:
                    # 下黑
                    if pix2 == 0:
                        code += pixel[reversal][3]
                    # 下白
                    else:
                        code += pixel[reversal][1]
                # 上白
                else:
                    # 下黑
                    if pix2 == 0:
                        code += pixel[reversal][0]
                    # 下白
                    else:
                        code += pixel[reversal][2]
            # 列索引超出高度，下白。超出的这一行区域必然全白
            else:
                pix1 = img.getpixel(
                    (x * cell_size[0] + int(cell_size[0] / 2), y * cell_size[1] + int(cell_size[1] / 2)))
                # 上黑
                if pix1 == 0:
                    code += pixel[reversal][1]
                # 上白
                else:
                    code += pixel[reversal][2]
        code += '\n'

    print(code)


if __name__ == '__main__':
    config = utils.load_json_file(path="./config/config.json")
    img = Image.open(config['qrcode_location'])
    show_img.print_qrcode(img=img)
