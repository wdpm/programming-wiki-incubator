package main

import "fmt"

//带缓冲的信道。将缓冲长度作为第二个参数提供给 make 来初始化一个带缓冲的信道：
//ch := make(chan int, 100)
//仅当信道的缓冲区填满后，向其发送数据时才会阻塞。当缓冲区为空时，接受方会阻塞。
func main() {
	ch := make(chan int, 2)
	ch <- 1
	ch <- 2

	// 当信道的缓冲区填满后，向其发送数据时才会阻塞
	// fatal error: all goroutines are asleep - deadlock!
	// ch <- 3

	fmt.Println(<-ch)
	fmt.Println(<-ch)

	// 当缓冲区为空时，接受方会阻塞
	// fatal error: all goroutines are asleep - deadlock!
	//fmt.Println(<-ch)
}
