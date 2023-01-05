# coding: utf-8
from PIL import Image

def main():
    # 打开文件获取Image对象
    image = Image.open('python.jpg')
    # 右上角的位置
    point = (image.size[0] - 1, 0)
    # 获取像素值
    pixel = image.getpixel(point)
    # 改写为反色的像素值
    image.putpixel(point, (
        255 - pixel[0],
        255 - pixel[1],
        255 - pixel[2]))
    # 图像保存至文件
    image.save('python_pixel.jpg')

if __name__ == '__main__':
    main()
