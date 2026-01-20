package main

import (
	"fmt"
)

func fibonacci(n int, c chan int) {
	x, y := 0, 1
	for i := 0; i < n; i++ {
		c <- x
		x, y = y, x+y
	}
	//发送者可通过 close 关闭一个信道来表示没有需要发送的值了。
	close(c)
}

//接收者可以通过为接收表达式分配第二个参数来测试信道是否被关闭：若没有值可以接收且信道已被关闭，那么在执行完
//v, ok := <-ch
//之后 ok 会被设置为 false。

func main() {
	c := make(chan int, 10)
	// Channel: the channel buffer capacity, in units of elements;
	// if v is nil, cap(v) is zero.
	go fibonacci(cap(c), c)
	//循环 for i := range c 会不断从信道接收值，直到它被关闭。
	for i := range c {
		fmt.Println(i)
	}

	v, ok := <-c
	fmt.Println(v, ok)
}

//0
//1
//1
//2
//3
//5
//8
//13
//21
//34
//0 false

