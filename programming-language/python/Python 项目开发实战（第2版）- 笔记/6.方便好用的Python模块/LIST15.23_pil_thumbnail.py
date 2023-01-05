# coding: utf-8
from PIL import Image

def main():
    # 打开文件获取Image对象
    image = Image.open('python.jpg')
    # 计算图像长宽的一半
    half_size = (image.size[0] / 2, image.size[1] / 2)
    # 图像大小降为一半
    image.thumbnail(half_size, Image.ANTIALIAS)
    # 图像保存至文件
    image.save('python_thumbnail.jpg')

if __name__ == '__main__':
    main()
