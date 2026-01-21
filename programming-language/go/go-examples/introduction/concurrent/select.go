package main

import "fmt"

func Fibonacci(c, quit chan int) {
	x, y := 0, 1
	for {
		select {
		case c <- x: // 将 x 发送至信道 c。
			x, y = y, x+y
		case <-quit: // block here
			fmt.Println("quit")
			return
		}
	}
}

//select 语句使一个 Go routine等待多个通信操作。
//select 会阻塞到某个分支可以继续执行为止，这时就会执行该分支。当多个分支都准备好时会随机选择一个执行。

func main() {
	c := make(chan int)
	quit := make(chan int)
	// 这段代码延迟执行
	go func() {
		for i := 0; i < 10; i++ {
			fmt.Println(<-c) //从信道 c读取值
		}
		// 打印 Fibonacci 数字后阻塞于 case <-quit

		// 向 quit信道赋值 0
		quit <- 0

		// case <-quit 接受到值之后，输出 quit ，然后返回
	}()
	// 立即执行
	Fibonacci(c, quit)
}
