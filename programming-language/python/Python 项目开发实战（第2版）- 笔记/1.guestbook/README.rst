===================
留言板应用
===================

目的
=====

练习开发通过Web浏览器提交留言的Web应用程序

工具版本
====================

:Python:     2.7.8
:pip:        1.5.6
:virtualenv: 1.11.6


安装与启动方法
=======================

从版本库获取代码，然后在该目录下搭建virtualenv环境::

   $ hg clone https://bitbucket.org/beproud/guestbook
   $ cd guestbook
   $ virtualenv .venv
   $ source .venv/bin/activate
   (.venv)$ pip install .
   (.venv)$ guestbook
    * Running on http://127.0.0.1:5000/


开发流程
=========

用于开发的安装
------------------

1. 检测
2. 按以下流程安装::

     (.venv)$ pip install -e .


变更依赖库时
---------------------

1. 更新``setup.py``的``install_requires``
2. 按以下流程更新环境::

     (.venv)$ virtualenv --clear .venv
     (.venv)$ pip install -e ./guestbook
     (.venv)$ pip freeze > requirements.txt

3. 将setup.py和requirements.txt提交到版本库

依赖包同时被 setup.py 和 requirements.txt 两个文件管理着。至于该用 setup.py 管理还是 requirements.txt 管理，
要视项目的公开方式或使用方式而定. 打包发布一般使用setup.py，只运行使用requirements.txt。

