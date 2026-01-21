package main

import (
	"fmt"
	"time"
)

func main() {
	messageCh := make(chan int, 10)
	disconnectCh := make(chan struct{})

	// go listing1(messageCh, disconnectCh)
	go listing2(messageCh, disconnectCh)

	for i := 0; i < 10; i++ {
		messageCh <- i
	}
	disconnectCh <- struct{}{}
	time.Sleep(10 * time.Millisecond)
}

func listing1(messageCh <-chan int, disconnectCh chan struct{}) {
	for {
		select {
		// 任何一个case进入的机会都是均匀分布的
		case v := <-messageCh:
			fmt.Println(v)
		case <-disconnectCh:
			fmt.Println("disconnection, return")
			return
		}
	}
}

// 0
// 1
// disconnection, return

func listing2(messageCh <-chan int, disconnectCh chan struct{}) {
	for {
		select {
		// 不管外层进入那个分支，都是先读取msg，最后才return。
		case v := <-messageCh:
			fmt.Println(v)
		case <-disconnectCh:
			for {
				select {
				case v := <-messageCh:
					fmt.Println(v)
				default:
					fmt.Println("disconnection, return")
					return
				}
			}
		}
	}
}

// 0
// 1
// 2
// 3
// 4
// 5
// 6
// 7
// 8
// 9
// disconnection, return
