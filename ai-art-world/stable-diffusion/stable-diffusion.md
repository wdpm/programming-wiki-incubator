# stable diffusion

### useful-links

- [SD-WebUI 安装方案 | AiDraw](https://guide.novelai.dev/guide/install/sd-webui)
- [基本指南 - AiDraw](https://draw.dianas.cyou/install/WebUi/set/#_2)
- [Prompt Search](https://www.ptsearch.info/articles/list_best/?page=4)
- [词图PromptTool](http://prompttool.com/NovelAI)
- [Stable Diffusion Models](https://rentry.co/sdmodels)
- [CUDA Benchmarks - Geekbench Browser](https://browser.geekbench.com/cuda-benchmarks)
- [「webui-user.bat」を編集する - としあきdiffusion Wiki*](https://wikiwiki.jp/sd_toshiaki/「webui-user.bat」を編集する)
- [[笔记\] 训练酸菜角色 stable-diffusion embeddings 简易指北 NGA玩家社区](https://ngabbs.com/read.php?tid=34067659&rand=665)
- [画风模型 | AI绘画模型博物馆](https://aimodel.subrecovery.top/2022/11/15/画风模型/)
- [Stable Diffusion](https://fluffy-bars-join-34-126-79-157.loca.lt/)
- [Run Data Science & Machine Learning Code Online | Kaggle](https://www.kaggle.com/code)
- [Stable Diffusion Web UI - a Hugging Face Space by camenduru](https://huggingface.co/spaces/camenduru/webui)
- [Best & Easiest Way to Run Stable Diffusion for Free (WebUI)](https://bytexd.com/best-way-to-run-stable-diffusion-for-free/)
- [Get Started With Stable Diffusion (Free) in Google Colab for AI Generated Art](https://bytexd.com/get-started-with-stable-diffusion-google-colab-for-ai-generated-art/)
- [元素法典——Novel AI 元素魔法全收录](https://docs.qq.com/doc/DWHl3am5Zb05QbGVs)
- [众神之谷 - 魔法小镇](https://kamiya.dev/stats.html)
- [全站资源导航 – AI绘图指南wiki](https://aiguidebook.top/index.php/全站资源导航/)
- [505465 - AiTags.Fun](https://aitags.fun/p.aspx?md5=d6ab3f35120b80a4e33887bdc0595b02)
- [魔导绪论](https://magic-tag.netlify.app/#/)
- [Danbooru 标签超市](https://tags.novelai.dev/)
- [【Novel AI】手把手教你Textual Inversion神经网络训练详细操作](https://www.bilibili.com/read/cv19088146)
- [【AI绘画】全网Stable Diffusion & NovelAI资源及使用技巧收集汇总（自用）](https://www.bilibili.com/read/cv19693040)
- [元素同典：确实不完全科学的魔导书](https://www.bilibili.com/read/cv19505389)
- [【AI绘画】完整tags书写思路，从人工智能理论来了解如何绘画](https://www.bilibili.com/read/cv19790550)



## parameters

### sample steps

下界为20，低于20则明显生成度不够，合理上界为40。高于40之后，训练很慢，而效果提升不明显。

### sampling method

采样方法很多，这里给出划分标准。如果画风明显有差异，那就自成一类。

如果相似性过大，一般取典型代表的取样方法即可。



Euler a ~~DPM++ 2S a~~ ~~DPM adaptive~~

Euler  ~~LMS~~ ~~DPM++ 2M~~

Heun ~~DPM2~~

DPM2 a

DPM++SDE

LMS Karras ~~DPM2 Karras~~

DPM2 a Karras ~~DPM++ 2S a Karras~~

DPM++ SDE Karras

DDIM ~~PLMS~~ ~~DPM++ 2M Karras~~



### width x height

一般而言，最小尺寸为 512 x 512 ,正方形。

- 768 x 512 
- 1024 x 768

不推荐更大的图片尺寸，因为非常吃显卡算力，速度很慢。

如果需要高分辨率，可以后续超分。



## batch count and batch size

batch count: generation count 批次。执行多少批。

batch size：一次生成多少张图片。

这里我们假设目标张数一定，例如需要8张图片。

那么有两种典型的策略：

- batch count 8 and batch size 1
- batch size 1 and batch size 8

比较时间差异。



### CFG scale

下界为4，低于4则生成度不够。上界推荐不要超过12。超过之后，涂抹感逐渐加剧。



### Seed

-1 表示随机。

具体的数字390407644表示一个确定的种子，用于固定一张图的生成。



## kaggle notebook

https://www.kaggle.com/imwdpm/code

- stable diffusion webui on kaggle
- Stable diffusioN AUTOMATIC1111



## extensions

[【NovelAI外接扩展图功能安装与使用教程,快来扩展你的图片吧!】 ](https://www.bilibili.com/video/BV1xR4y1y7K9/?share_source=copy_web&vd_source=2c098338ec2cc057ab6aec529c13d324)



## train

[【NovelAI】利用Colab来在线训练自己的tag，以Hiten画风tag为例](https://www.bilibili.com/video/BV1EK411Q7W4/share_source=copy_web&vd_source=2c098338ec2cc057ab6aec529c13d324)

