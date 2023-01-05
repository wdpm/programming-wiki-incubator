# coding: utf-8
from PIL import Image

def main():
    # 打开文件获取Image对象
    image = Image.open('python.jpg')
    # 根据短边长度求中央正方形的坐标
    if image.size[0] < image.size[1]:
        # 横边较短时（瘦高的图像）
        crop_rect = (
            0,
            (image.size[1] - image.size[0]) / 2,
            image.size[0],
            (image.size[1] - image.size[0]) / 2 + image.size[0])
    else:
        # 竖边较短时（矮胖的图像）
        crop_rect = (
            (image.size[0] - image.size[1]) / 2,
            0,
            (image.size[0] - image.size[1]) / 2 + image.size[1],
            image.size[1])
    # 剪裁
    image_croped = image.crop(crop_rect)
    # 图像保存至文件
    image_croped.save('python_crop.jpg')

if __name__ == '__main__':
    main()
