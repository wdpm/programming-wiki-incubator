// This sample program demonstrates how to create race
// conditions in our programs. We don't want to do this.
package main

import (
	"fmt"
	"runtime"
	"sync"
)

var (
	// counter is a variable incremented by all goroutines.
	counter int

	// wg is used to wait for the program to finish.
	wg sync.WaitGroup
)

// main is the entry point for all Go programs.
func main() {
	// Add a count of two, one for each goroutine.
	wg.Add(2)

	// Create two goroutines.
	go incCounter(1)
	go incCounter(2)

	// Wait for the goroutines to finish.
	wg.Wait()
	fmt.Println("Final Counter:", counter)
}

// incCounter increments the package level counter variable.
func incCounter(id int) {
	// Schedule the call to Done to tell main we are done.
	defer wg.Done()

	for count := 0; count < 2; count++ {
		// Capture the value of Counter.
		value := counter

		// Yield the thread and be placed back in queue.
		runtime.Gosched()

		// Increment our local value of Counter.
		value++

		// Store the value back into Counter.
		counter = value
	}
}

// 变量 counter 会进行 4 次读和写操作，每个 goroutine 执行两次。但是，程序终止时，counter
// 变量的值为 2

// 每个 goroutine 都会覆盖另一个 goroutine 的工作。这种覆盖发生在 goroutine 切换的时候。每
// 个 goroutine 创造了一个 counter 变量的副本，之后就切换到另一个 goroutine。当这个 goroutine
// 再次运行的时候，counter 变量的值已经改变了，但是 goroutine 并没有更新自己的那个副本的
// 值，而是继续使用这个副本的值，用这个值递增，并存回 counter 变量，结果覆盖了另一个
// goroutine 完成的工作

// go build --race

// ==================
// WARNING: DATA RACE
// Read at 0x0000006386d0 by goroutine 8:
// main.main.func1()
// D:/Code/MyGithubProjects/go-in-action/chapter6/listing09/listing09.go:25 +0x30
//
// Goroutine 8 (running) created at:
// main.main()
// D:/Code/MyGithubProjects/go-in-action/chapter6/listing09/listing09.go:26 +0x50
//
// Goroutine 7 (finished) created at:
// main.main()
// D:/Code/MyGithubProjects/go-in-action/chapter6/listing09/listing09.go:25 +0x44
// ==================
// Final Counter: 4
// Found 1 data race(s)
