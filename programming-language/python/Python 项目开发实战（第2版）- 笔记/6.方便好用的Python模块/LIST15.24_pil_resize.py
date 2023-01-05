# coding: utf-8
from PIL import Image

def main():
    # 打开文件获取Image对象
    image = Image.open('python.jpg')
    # 计算图像长度的2倍
    double_size = (image.size[0], image.size[1] * 2)
    # 图像大小增加至2倍
    image_resized = image.resize(double_size, Image.ANTIALIAS)
    # 图像保存至文件
    image_resized.save('python_resize.jpg')

if __name__ == '__main__':
    main()
