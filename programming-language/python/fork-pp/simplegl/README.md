# install

这里以windows 10平台为例，首先确定系统版本，然后确定 python 版本。

下载对应的OpenGL.whl文件

- PyOpenGL-3.1.6-cp37-cp37m-win_amd64.whl
- PyOpenGL_accelerate-3.1.6-cp37-cp37m-win_amd64.whl

```
> python -V -V
Python 3.7.4 (tags/v3.7.4:e09359112e, Jul  8 2019, 20:34:20) [MSC v.1916 64 bit (AMD64)]

> pip install PyOpenGL-3.1.6-cp37-cp37m-win_amd64.whl
> pip install PyOpenGL_accelerate-3.1.6-cp37-cp37m-win_amd64.whl
```

## PYTHONPATH

在 windows 10 环境下，在系统环境变量以及用户环境变量，添加 `D:\Code\OtherGithubProjects\pp\common` 这个路径到 PYTHONPATH 。
此外，在 PATH 中补充 %PYTHONPATH% 。

- 如果在 PyCharm 的 terminal 中运行，那么 PYTHONPATH 这个路径设置并没有生效。
  
  尝试：在项目设置中设置 common这个目录为 Sources。
  ```bash
  import sys; print('Python %s on %s' % (sys.version, sys.platform))
  sys.path.extend(['D:\\Code\\OtherGithubProjects\\pp', 'D:\\Code\\OtherGithubProjects\\pp\\common', 'D:/Code/OtherGithubProjects/pp'])
  ```
  注意到 `D:\\Code\\OtherGithubProjects\\pp\\common` 已经被添加了，依旧无效。暂时放弃。
  
- 如果在 windows terminal 中运行，可以正确识别 PYTHONPATH 的作用。

## GLFW library

前往[这个页面](https://www.glfw.org/download.html) 进行下载，之后设置系统变量。
- GLFW_LIBRARY
- C:\glfw-3.3.8.bin.WIN64\lib-mingw-w64\glfw3.dll

## run
请在 windows terminal 中执行:
```bash
python simpleglfw.py
```
