package main

import (
	"fmt"
	"sync"
	"sync/atomic"
)

// happens-before impl using sync.WaitGroup

func listing1() {
	wg := sync.WaitGroup{}
	var v uint64

	for i := 0; i < 3; i++ {
		go func() {
			// The problem is that wg.Add(1) is called within the newly created goroutine, not in
			// the parent goroutine. Hence, there is no guarantee that we have indicated to the wait
			// group that we want to wait for three goroutines before calling wg.Wait()
			wg.Add(1)
			atomic.AddUint64(&v, 1)
			wg.Done()
		}()
	}

	wg.Wait()
	fmt.Println(v)
}

// ok
func listing2() {
	wg := sync.WaitGroup{}
	var v uint64

	// 如果能提前知道等待量N是多少，那就在外层设置。
	wg.Add(3)
	for i := 0; i < 3; i++ {
		go func() {
			atomic.AddUint64(&v, 1)
			wg.Done()
		}()
	}

	wg.Wait()
	fmt.Println(v)
}

// ok
func listing3() {
	wg := sync.WaitGroup{}
	var v uint64

	for i := 0; i < 3; i++ {
		// 如果不能提前知道，那也要在goroutine之外设置
		wg.Add(1)
		go func() {
			atomic.AddUint64(&v, 1)
			wg.Done()
		}()
	}

	wg.Wait()
	fmt.Println(v)
}
