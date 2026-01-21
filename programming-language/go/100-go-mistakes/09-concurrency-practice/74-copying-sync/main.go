package main

import (
	"sync"
	"time"
)

// sync types shouldn’t be copied, should be shared.

//  sync.Cond
//  sync.Map
//  sync.Mutex
//  sync.RWMutex
//  sync.Once
//  sync.Pool
//  sync.WaitGroup

func main() {
	counter := NewCounter()

	go func() {
		counter.Increment1("foo")
	}()
	go func() {
		counter.Increment1("bar")
	}()

	time.Sleep(10 * time.Millisecond)
}

type Counter struct {
	mu       sync.Mutex
	counters map[string]int
}

func NewCounter() Counter {
	return Counter{counters: map[string]int{}}
}

// IDE warning: 'Increment1' passes a lock by the value: type 'Counter' contains 'sync.Mutex' which is 'sync.Locker'
// it performs a copy of the Counter struct, which also copies the mutex.
func (c Counter) Increment1(name string) {
	c.mu.Lock()
	defer c.mu.Unlock()
	c.counters[name]++
}

// solution1: use pointer as a receiver
func (c *Counter) Increment2(name string) {
	// Same code
}

type Counter2 struct {
	// solution2: use pointer as a mutex
	mu       *sync.Mutex
	counters map[string]int
}

func NewCounter2() Counter2 {
	return Counter2{
		mu:       &sync.Mutex{},
		counters: map[string]int{},
	}
}

// As a rule of thumb, whenever multiple goroutines have to access a common sync ele-
// ment, we must ensure that they all rely on the same instance.
