package main

import (
	"fmt"
	"time"
)

func say(s string) {
	for i := 0; i < 5; i++ {
		time.Sleep(100 * time.Millisecond)
		fmt.Println(s)
	}
}

// goroutine 是由 Go 运行时管理的轻量级线程
func main() {
	//say("world")的求值发生在当前的 Go 程中，而 say("world") 的执行发生在新的 Go 程中
	go say("world")
	say("hello")
}

// 每次结果都是交错的，但不相同
//hello
//world
//world
//hello
//hello
//world
//world
//hello
//hello
