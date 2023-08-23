# stable diffusion

## parameters

### sample steps

下界为 20，低于 20 则明显生成度不够，合理上界为 40。高于 40 之后，训练很慢，而效果提升不明显。

### sampling method

采样方法很多，这里给出划分标准。如果画风明显有差异，那就自成一类。

如果相似性过大，一般取典型代表的取样方法即可。

- Euler a ~~DPM++ 2S a~~ ~~DPM adaptive~~
- Euler  ~~LMS~~ ~~DPM++ 2M~~
- Heun ~~DPM2~~
- DPM2 a
- DPM++SDE
- LMS Karras ~~DPM2 Karras~~
- DPM2 a Karras ~~DPM++ 2S a Karras~~
- DPM++ SDE Karras
- DDIM ~~PLMS~~ ~~DPM++ 2M Karras~~

### width x height

一般而言，最小尺寸为 512 x 512 , 正方形。

- 768 x 512
- 1024 x 768

不推荐更大的图片尺寸，因为非常吃显卡算力，速度很慢。

如果需要高分辨率，可以后续超分。

## batch count and batch size

batch count: generation count 批次。执行多少批。

batch size：一次生成多少张图片。

这里我们假设目标张数一定，例如需要 8 张图片。

那么有两种典型的策略：

- batch count 8 and batch size 1
- batch size 1 and batch size 8

比较时间差异。

### CFG scale

下界为 4，低于 4 则生成度不够。上界推荐不要超过 12。超过之后，涂抹感逐渐加剧。

### Seed

-1 表示随机。

具体的数字 390407644 表示一个确定的种子，用于固定一张图的生成。

## kaggle notebook

https://www.kaggle.com/imwdpm/code

- stable diffusion webui on kaggle
- Stable diffusioN AUTOMATIC1111


## 元素法典——节选

```
元素法典——Novel AI 元素魔法全收录
------------
空间法
幻之时
苇名法
冰系改
城堡法
雪月法
黄昏法
森林法
蔷薇法
坠落法
秘境法
摄影法
天选术
血魔法
星源法
浮世绘
----------

元素法典第一点五卷——Novel AI 元素魔法全收录
-------------
暗鸦法
沉入星海
百溺法
森罗法
血法改
星语术
---------------

元素法典 第二卷——Novel AI 元素魔法全收
--------
故障艺术
漫画风格
繁浮法：在anything模型效果完全不同
人像水墨法
水镜法
天堂台阶
彩漆法
林奈法
夏夜之狐
群像法 Lite
向日葵法
彷徨
国风少女
未名花
---------------

元素法典 第二点五卷——Novel AI 元素魔法全收录
-----
云海白鹤
下午茶
孔雀仙
冰与火之歌
立绘法
秋水之淚
水龙吟
水裙法
黑蛊
闲潭梦花
手绘法
云作壁上观
------------
```
