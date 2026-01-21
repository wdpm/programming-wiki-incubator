package main

import (
	"fmt"
	"sync"
	"sync/atomic"
)

func listing1() {
	i := 0

	go func() {
		i++
	}()

	go func() {
		i++
	}()
}

// use atomic value
func listing2() {
	var i int64

	go func() {
		atomic.AddInt64(&i, 1)
	}()

	go func() {
		atomic.AddInt64(&i, 1)
	}()
}

func listing3() {
	i := 0
	mutex := sync.Mutex{}

	go func() {
		mutex.Lock()
		i++
		mutex.Unlock()
	}()

	go func() {
		mutex.Lock()
		i++
		mutex.Unlock()
	}()
}

// use chan
func listing4() {
	i := 0
	ch := make(chan int)

	go func() {
		ch <- 1
	}()

	go func() {
		ch <- 1
	}()

	i += <-ch
	i += <-ch
}

// use mutex
func listing5() {
	i := 0
	mutex := sync.Mutex{}

	go func() {
		mutex.Lock()
		defer mutex.Unlock()
		i = 1
	}()

	go func() {
		mutex.Lock()
		defer mutex.Unlock()
		i = 2
	}()

	//  1 or 2, non-deterministic
	fmt.Println(i)

	_ = i
}

func main() {
	listing5()
}
