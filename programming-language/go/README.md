# lets-go
Learn Go.

## 安装
- [Installing Go](https://golang.org/doc/install)

## 配置
- [Setting GOPATH](https://github.com/golang/go/wiki/SettingGOPATH)
- [Go Proxy](https://goproxy.io/)
- [GoLand IDE Setting GOPATH](docs/GoLand-config.md)

## 入门
> 参阅 [A Tour of Go Excercises](https://tour.go-zh.org/)

- [如何写 Go 代码](docs/how-to-write-go-code.md)
- 基础
  - 函数 func
  - 包 package
  - 变量 var
  - 基本类型 bool string int? uint? byte rune float? complex?
  - 流程控制 if for switch defer
  - 指针地址 &i 和指针读值 *p
  - 结构体 struct
  - 数组 var a [2]string
  - 数组切片 s[1:4]，长度len(s)和容量cap(s)，0值nil，range遍历
  - 创建 make
  - 映射 map
- 方法、接口、其他
  - 方法 method，定义中有调用者
  - 接口 interface
  - 错误 error
  - 图像 image
- 并发
  - 子程序 go routine
  - 信道 chan，缓冲大小，主动关闭 close()
  - select 语句等待多个通信操作
  - 互斥锁 sync.Mutex
  - Web 简易爬虫

## 教程
- [package 手册](https://go-zh.org/pkg/)
- [Go 编程语言规范](https://go-zh.org/ref/spec)
- [Go 并发模型](https://www.youtube.com/watch?v=f6kdp27TYZs)
- [深入 Go 并发模型](https://www.youtube.com/watch?v=QDDwwePbDtw)
- [通过通信共享内存](https://go-zh.org/doc/codewalk/sharemem/)
- [Go: a simple programming environment](https://vimeo.com/53221558)
- [Writing Web Applications](https://go-zh.org/doc/articles/wiki/)
- [Go 中的一等函数](https://go-zh.org/doc/codewalk/functions/)

## 书籍
- [Go Web 编程](https://github.com/astaxie/build-web-application-with-golang)




