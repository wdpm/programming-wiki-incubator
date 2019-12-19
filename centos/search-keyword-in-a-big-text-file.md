# 文本文件中查找关键字

## nano
```bash
nano sometext.txt
```
1. Ctrl+W 弹出搜索栏
2. 搜索栏中输入keyword
3. Enter键确认。
4. Alt+W搜索下一个结果。

> Xshell中，解决左Alt键冲突。
> 文件->当前会话属性->终端->键盘->勾选"将左ALT用作Meta键"。

## vi
```bash
vi sometext.txt
```
1. 命令模式下输入"/",然后输入keyword，例如"/keyword"。
2. 查找下一个，按"n"。
3. 关于退出vi：先按i，再按ESC，然后输出":wq"保存退出。

## less
```bash
less sometext.txt
```
1. Shift + G到文件尾部
2. 输入“?keyword”，按enter键确认，关键字会被高亮显示。
3. 按q直接退出。

## tail
```bash
tail -n 50 sometext.txt
```
会显示该文件末尾50行的内容。

```bash
tail -f sometext.txt
```
开启监听模式，常用于监听log

## grep
```bash
grep keyword sometext.text
```
在终端会直接高亮输出含有该关键字的行。
