# coding: utf-8
from PIL import Image

def main():
    # 打开文件获取Image对象
    image = Image.open('python.jpg')
    buffer = []
    # 循环逐一获取图像的像素
    for pixel in image.getdata():
        # 将像素反色并存入缓冲区
        buffer.append((
            255 - pixel[0],
            255 - pixel[1],
            255 - pixel[2]))
    # 用缓冲区内的像素覆盖原有数据
    image.putdata(buffer)
    # 图像保存至文件
    image.save('python_filter.jpg')

if __name__ == '__main__':
    main()
