// This sample program demonstrates how to use the atomic
// package functions Store and Load to provide safe access
// to numeric types.
package main

import (
	"fmt"
	"sync"
	"sync/atomic"
	"time"
)

var (
	// shutdown is a flag to alert running goroutines to shutdown.
	shutdown int64

	// wg is used to wait for the program to finish.
	wg sync.WaitGroup
)

// main is the entry point for all Go programs.
func main() {
	// Add a count of two, one for each goroutine.
	wg.Add(2)

	// Create two goroutines.
	go doWork("A")
	go doWork("B")

	// Give the goroutines time to run.
	time.Sleep(1 * time.Second)

	// Safely flag it is time to shutdown.
	fmt.Println("Shutdown Now")
	atomic.StoreInt64(&shutdown, 1)

	// Wait for the goroutines to finish.
	wg.Wait()
}

// doWork simulates a goroutine performing work and
// checking the Shutdown flag to terminate early.
func doWork(name string) {
	// Schedule the call to Done to tell main we are done.
	defer wg.Done()

	for {
		fmt.Printf("Doing %s Work\n", name)
		time.Sleep(250 * time.Millisecond)

		// Do we need to shutdown.
		if atomic.LoadInt64(&shutdown) == 1 {
			fmt.Printf("Shutting %s Down\n", name)
			break
		}
	}
}

// 主线程等待 1s，而 goroutines 每个都是间隔 250ms 输出一次，因为 A 和 B 各自输出 4 次 work。

// Doing B Work
// Doing A Work
// Doing A Work
// Doing B Work
// Doing B Work
// Doing A Work
// Doing A Work
// Doing B Work
// Shutdown Now
// Shutting A Down
// Shutting B Down
