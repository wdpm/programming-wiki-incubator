# setup venv

> [guide](https://packaging.python.org/en/latest/guides/installing-using-pip-and-virtual-environments/#creating-a-virtual-environment)

## setup
```bash
py -m venv venv
```
```bash
.\venv\Scripts\activate
```

```
where python

D:\Code\MyLocalProjects\python-consurrency-with-asyncio-notes\venv\Scripts\python.exe
C:\Python\Python37\python.exe
C:\miniconda3\python.exe
C:\Users\wdpm\AppData\Local\Programs\Python\Python310\python.exe
C:\Users\wdpm\AppData\Local\Microsoft\WindowsApps\python.exe
```

```bash
.\venv\Scripts\python.exe

Python 3.10.7 (tags/v3.10.7:6cc6b13, Sep  5 2022, 14:08:36) [MSC v.1933 64 bit (AMD64)] on win32
Type "help", "copyright", "credits" or "license" for more information.
>>>
```

## Leaving the virtual environment
```bash
deactivate
```

## install packages

```bash
py -m pip install requests
```

## Using requirements files
```bash
py -m pip install -r requirements.txt
```

## Freezing dependencies

```bash 
py -m pip freeze
```