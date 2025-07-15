# python库对比

## python standard libraries

| name                                                         | desc                                              |
| ------------------------------------------------------------ | ------------------------------------------------- |
| logging                                                      | 日志                                              |
| datetime                                                     | 时间日期处理                                      |
| textwrap                                                     | 复杂文本的包装库，例如反缩进                      |
| timeit                                                       | 衡量函数执行时间                                  |
| enum                                                         | 枚举基类                                          |
| collections.defaultdict                                      | 含有默认初始值的字典,可以给字典的默认行为加上变化 |
| collections.abc.MutableMapping                               | 可变的字典                                        |
| collections.deque                                            | 双端队列                                          |
| copy                                                         | 浅复制copy和深复制deepcopy                        |
| collections.namedtuple                                       | 具名元组                                          |
| random                                                       | 随机数生成                                        |
| bisect                                                       | 二分法                                            |
| contextlib                                                   | @contextmanager 和 @asynccontextmanager decorator |
| itertools                                                    | 迭代工具库，笛卡儿积                              |
| functools.partial                                            | 偏函数                                            |
| functools.wraps                                              | 修复函数嵌套时丢失原函数属性                      |
| functools.lrucache                                           | 最近最少使用工具类                                |
| abc.ABC                                                      | 抽象类                                            |
| collections.Counter                                          | 计数器统计dict                                    |
| functools.total_ordering                                     | 简化6个比较关系到2个方法实现                      |
| dataclasses.dataclass                                        | 数据类，省去hash、eq、init方法                    |
| pathlib                                                      | 路径处理                                          |
| [mimetypes](https://docs.python.org/3/library/mimetypes.html) | Map filenames to MIME types.                      |

## python thirty libraries

| name                                              | desc                        |
|---------------------------------------------------|-----------------------------|
| dis                                               | 反编译python代码，用于调试            |
| jinjia2                                           | 纯python的模版引擎，用于渲染长字符串       |
| sqlalchemy                                        | 著名的python ORM库，用于和常见关系数据库交互 |
| [loguru](https://github.com/Delgan/loguru)        | python日志库                   |
| isort                                             | import语句排序                  |
| black                                             | 强制格式化                       |
| flake8                                            | linter                      |
| [ruff](https://github.com/astral-sh/ruff)                                              | linter                      |
| pre-commit                                        | git hooks                   |
| mypy                                              | 类型加强                        |
| pytest                                            | 单元测试                        |
| [httptest](https://github.com/pdxjohnny/httptest) | 启发于go的httptest              |
| [tenacity](https://github.com/jd/tenacity)        | python 的重试库                 |
| [sympy](https://www.sympy.org/)                   | python的数学符号库                |
| [结巴](https://github.com/fxsjy/jieba)                  | 中文分词                        |
| https://github.com/lk-geimfari/mimesis                  | data faker/mock             |

## need to explore

网络测试模拟库
- VCR.py
- Responses
- HTTPretty
- Betamax

文本处理库
- https://github.com/sqids/sqids-python hashids，将数字id转化为字符ID，用于隐藏数据库真实主键。类似于B站的BV号。
  - https://sqids.org/python 文档
  - https://github.com/sqids/sqids-spec 实现的规约

ASGI Servers
- [uvicorn](https://github.com/encode/uvicorn) - A lightning-fast ASGI server implementation, using uvloop and httptools.

OAuth
- [authlib](https://github.com/lepture/authlib) - JavaScript Object Signing and Encryption draft implementation.
- [oauthlib](https://github.com/oauthlib/oauthlib) - A generic and thorough implementation of the OAuth request-signing logic.

JWT
- [pyjwt](https://github.com/jpadilla/pyjwt) - JSON Web Token implementation in Python.

Terminal Rendering
- [rich](https://github.com/Textualize/rich) - Python library for rich text and beautiful formatting in the terminal. Also provides a great RichHandler log handler.
- [tqdm](https://github.com/tqdm/tqdm) - Fast, extensible progress bar for loops and CLI.

Computer Vision
- [opencv](https://opencv.org/) - Open Source Computer Vision Library.
- [pytesseract](https://github.com/madmaze/pytesseract) - A wrapper for Google Tesseract OCR.
- [tesserocr](https://github.com/sirfz/tesserocr) - Another simple, Pillow-friendly, wrapper around the tesseract-ocr API for OCR.

Data Analysis

- [pandas](http://pandas.pydata.org/) - A library providing high-performance, easy-to-use data structures and data analysis tools.

Data Visualization

- [bokeh](https://github.com/bokeh/bokeh) - Interactive Web Plotting for Python.
- [matplotlib](https://github.com/matplotlib/matplotlib) - A Python 2D plotting library.
- [pygraphviz](https://github.com/pygraphviz/pygraphviz/) - Python interface to [Graphviz](http://www.graphviz.org/).
- [seaborn](https://github.com/mwaskom/seaborn) - Statistical data visualization using Matplotlib.

Deep Learning

- [keras](https://github.com/keras-team/keras) - A high-level neural networks library and capable of running on top of either TensorFlow or Theano.
- [pytorch](https://github.com/pytorch/pytorch) - Tensors and Dynamic neural networks in Python with strong GPU acceleration.
- [tensorflow](https://github.com/tensorflow/tensorflow) - The most popular Deep Learning framework created by Google.

Distribution

- [py2app](https://github.com/ronaldoussoren/py2app) - Freezes Python scripts (Mac OS X).
- [py2exe](https://github.com/py2exe/py2exe) - Freezes Python scripts (Windows).
- [pyinstaller](https://github.com/pyinstaller/pyinstaller) - Converts Python programs into stand-alone executables (cross-platform).

Documentation

- [sphinx](https://github.com/sphinx-doc/sphinx/) - Python Documentation generator.
- mkdocs

GUI Development

- [reflex](https://github.com/reflex-dev/reflex)
  - Pure Python, No frontend. React frontend + FastAPI backend app. uses WebSockets to communicate between the frontend and the backend
  - 封装前端元素的使用为python API，不使用原生HTML。
- [nicegui](https://nicegui.io/#features) 
  - Vue to Python bridge + Quasar + FastAPI
  - 封装前端元素为python API，不使用原生HTML。
- [flet](https://github.com/flet-dev/flet) 
  - Flet UI is built with Flutter framework.
  - 将flutter移植到python GUI生态，组件样式类似flutter，不使用原生HTML。
- [pywebview](https://github.com/r0x0r/pywebview) 
  - a lightweight web framework like Flask (opens new window)or on its own with a two way bridge between Python and DOM.
  - 使用原生HTML。
- [Eel](https://github.com/r0x0r/pywebview)
  - 使用原生HTML。

HTML Manipulation

- [beautifulsoup](https://www.crummy.com/software/BeautifulSoup/bs4/doc/) - Providing Pythonic idioms for iterating, searching, and modifying HTML or XML.
- [lxml](http://lxml.de/) - A very fast, easy-to-use and versatile library for handling HTML and XML.

HTTP Clients

- [httpx](https://github.com/encode/httpx) - A next generation HTTP client for Python.
- [requests](https://github.com/psf/requests) - HTTP Requests for Humans.
- aiohttp

Image Processing

- [pillow](https://github.com/python-pillow/Pillow) - Pillow is the friendly [PIL](http://www.pythonware.com/products/pil/) fork.

Logging

- [logging](https://docs.python.org/3/library/logging.html) - (Python standard library) Logging facility for Python.
- [loguru](https://github.com/Delgan/loguru) - Library which aims to bring enjoyable logging in Python.

ORM

- Relational Databases
  - [Django Models](https://docs.djangoproject.com/en/dev/topics/db/models/) - The Django ORM.
  - SQLAlchemy \- The Python SQL Toolkit and Object Relational Mapper.

Package Management

- pip The package installer for Python.
- [conda](https://github.com/conda/conda/) - Cross-platform, Python-agnostic binary package manager.
- [poetry](https://github.com/sdispater/poetry) - Python dependency management and packaging made easy.

RESTful API

- Django
  - [django-rest-framework](https://github.com/encode/django-rest-framework) - A powerful and flexible toolkit to build web APIs.
- Flask
  - [eve](https://github.com/pyeve/eve) - REST API framework powered by Flask, MongoDB and good intentions.
  - [flask-api](https://github.com/flask-api/flask-api) - Browsable Web APIs for Flask.
  - [flask-restful](https://github.com/flask-restful/flask-restful) - Quickly building REST APIs for Flask.
- Framework agnostic
  - [falcon](https://github.com/falconry/falcon) - A high-performance framework for building cloud APIs and web app backends.
  - [fastapi](https://github.com/tiangolo/fastapi) - A modern, fast, web framework for building APIs with Python 3.6+ based on standard Python type hints.

Science

- [NumPy](http://www.numpy.org/) - A fundamental package for scientific computing with Python.
- [SciPy](https://www.scipy.org/) - A Python-based ecosystem of open-source software for mathematics, science, and engineering.

Specific Formats Processing

- PDF
  - [pdfminer.six](https://github.com/pdfminer/pdfminer.six) - Pdfminer.six is a community maintained fork of the original PDFMiner.
  - [PyPDF2](https://github.com/mstamy2/PyPDF2) - A library capable of splitting, merging and transforming PDF pages.
  - [ReportLab](https://www.reportlab.com/opensource/) - Allowing Rapid creation of rich PDF documents.
- Markdown
  - [Mistune](https://github.com/lepture/mistune) - Fastest and full featured pure Python parsers of Markdown.
  - [Python-Markdown](https://github.com/waylan/Python-Markdown) - A Python implementation of John Gruber’s Markdown.
- YAML
  - [PyYAML](http://pyyaml.org/) - YAML implementations for Python.
- CSV
  - [csvkit](https://github.com/wireservice/csvkit) - Utilities for converting to and working with CSV.

Task Queues

- [celery](https://docs.celeryproject.org/en/stable/) - An asynchronous task queue/job queue based on distributed message passing.

Template Engine

- [Genshi](https://genshi.edgewall.org/) - Python templating toolkit for generation of web-aware output.
- [Jinja2](https://github.com/pallets/jinja) - A modern and designer friendly templating language.

Testing

> 需要研究调查，删减

- Testing Frameworks
  - [pytest](https://docs.pytest.org/en/latest/) - A mature full-featured Python testing tool.
  - [unittest](https://docs.python.org/3/library/unittest.html) - (Python standard library) Unit testing framework.
- Test Runners
  - [green](https://github.com/CleanCut/green) - A clean, colorful test runner.
  - [mamba](http://nestorsalceda.github.io/mamba/) - The definitive testing tool for Python. Born under the banner of BDD.
  - [tox](https://tox.readthedocs.io/en/latest/) - Auto builds and tests distributions in multiple Python versions
- GUI / Web Testing
  - [locust](https://github.com/locustio/locust) - Scalable user load testing tool written in Python.
  - [PyAutoGUI](https://github.com/asweigart/pyautogui) - PyAutoGUI is a cross-platform GUI automation Python module for human beings.
  - [Schemathesis](https://github.com/kiwicom/schemathesis) - A tool for automatic property-based testing of web applications built with Open API / Swagger specifications.
  - [Selenium](https://pypi.org/project/selenium/) - Python bindings for [Selenium](http://www.seleniumhq.org/) WebDriver.
  - [sixpack](https://github.com/seatgeek/sixpack) - A language-agnostic A/B Testing framework.
  - [splinter](https://github.com/cobrateam/splinter) - Open source tool for testing web applications.
- Mock
  - [doublex](https://pypi.org/project/doublex/) - Powerful test doubles framework for Python.
  - [freezegun](https://github.com/spulec/freezegun) - Travel through time by mocking the datetime module.
  - [httmock](https://github.com/patrys/httmock) - A mocking library for requests for Python 2.6+ and 3.2+.
  - [httpretty](https://github.com/gabrielfalcao/HTTPretty) - HTTP request mock tool for Python.
  - [mock](https://docs.python.org/3/library/unittest.mock.html) - (Python standard library) A mocking and patching library.
  - [mocket](https://github.com/mindflayer/python-mocket) - A socket mock framework with gevent/asyncio/SSL support.
  - [responses](https://github.com/getsentry/responses) - A utility library for mocking out the requests Python library.
  - [VCR.py](https://github.com/kevin1024/vcrpy) - Record and replay HTTP interactions on your tests.
- Object Factories
  - [factory_boy](https://github.com/FactoryBoy/factory_boy) - A test fixtures replacement for Python.
  - [mixer](https://github.com/klen/mixer) - Another fixtures replacement. Supports Django, Flask, SQLAlchemy, Peewee and etc.
  - [model_mommy](https://github.com/vandersonmota/model_mommy) - Creating random fixtures for testing in Django.
- Code Coverage
  - [coverage](https://pypi.org/project/coverage/) - Code coverage measurement.
- Fake Data
  - [fake2db](https://github.com/emirozer/fake2db) - Fake database generator.
  - [faker](https://github.com/joke2k/faker) - A Python package that generates fake data.
  - [mimesis](https://github.com/lk-geimfari/mimesis) - is a Python library that help you generate fake data.
  - [radar](https://pypi.org/project/radar/) - Generate random datetime / time.

Text Processing

- General
  - [pangu.py](https://github.com/vinta/pangu.py) - Paranoid text spacing.
  - [pypinyin](https://github.com/mozillazg/python-pinyin) - Convert Chinese hanzi (漢字) to pinyin (拼音).
- Slugify
  - [awesome-slugify](https://github.com/dimka665/awesome-slugify) - A Python slugify library that can preserve unicode.
  - [python-slugify](https://github.com/un33k/python-slugify) - A Python slugify library that translates unicode to ASCII.
  - [unicode-slugify](https://github.com/mozilla/unicode-slugify) - A slugifier that generates unicode slugs with Django as a dependency.
- Unique identifiers
  - [hashids](https://github.com/davidaurelio/hashids-python) - Implementation of [hashids](http://hashids.org/) in Python.
  - [shortuuid](https://github.com/skorokithakis/shortuuid) - A generator library for concise, unambiguous and URL-safe UUIDs.
- Parser
  - [pygments](http://pygments.org/) - A generic syntax highlighter.

Web Frameworks

- Synchronous
  - django
  - flask - A microframework for Python.
- Asynchronous
  - [tornado](https://github.com/tornadoweb/tornado) - A web framework and asynchronous networking library.

WSGI Servers

- [gunicorn](https://github.com/benoitc/gunicorn) - Pre-forked, ported from Ruby's Unicorn project.
- [uwsgi](https://uwsgi-docs.readthedocs.io/en/latest/) - A project aims at developing a full stack for building hosting services, written in C.
- [waitress](https://github.com/Pylons/waitress) - Multi-threaded, powers Pyramid.
- [werkzeug](https://github.com/pallets/werkzeug) - A WSGI utility library for Python that powers Flask and can easily be embedded into your own projects.