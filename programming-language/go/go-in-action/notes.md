# notes

## chapter 7
- runner 一个goroutine的调度器，用于使用多个goroutines来执行多个任务。可以继续扩展功能。
- pool 池化技术，重用珍贵的系统资源，【请求-使用-归还】模式。
- work 使用无缓冲chan，强调实时交付数据到一定数量限制的goroutines。
- search 没有说明。
- semaphore 没有说明。

## chapter 8

1.19.2 标准库：
```
archive => tar/zip 归档文件
bufio
builtin => 内置库，不要显式import
bytes => 字节操作库，例如Buffer
compress => bzip2/flate/gazip/lzw/zlib，压缩文件，注意和归档区分。
cmd => 
container => heap/list/ring, 三种容器数据结构
context => 上下文，提供取消signal、截止期限等功能。
crypto => 加密算法
database => 数据库交互
debug => 调试
embed => use the //go:embed directive to initialize a variable
encoding => 编码，例如ascii85、base64、base32、csv、hex、json、xml
errors => 错误工具库
expvar => provides a standardized interface to public variables
flag => 标志工具库，例如command line args的解析
fmt => 格式化输出
go => go语言本身的代码
hash => 提供了多种hash函数
html => HTML文本格式相关
image => implements a basic 2-D image library
index
io => IO相关
log => 日志
math => 数学
mime
net
os => 系统
path => 路径
reflect
regexp 
runtime => 运行时环境，例如物理CPU可用核心数
sort
strconv 
strings => 字符串
sync
syscall
testing 
text
time => 时间
unicode unsafe
```