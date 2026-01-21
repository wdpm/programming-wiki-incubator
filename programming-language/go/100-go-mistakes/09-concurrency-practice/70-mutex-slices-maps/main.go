package main

import (
	"fmt"
	"sync"
)

func main() {
	c := Cache{
		balances: make(map[string]float64),
	}
	c.AddBalance("1", 1.0)
	c.AddBalance("2", 3.0)
	fmt.Println(c.AverageBalance1())
	fmt.Println(c.AverageBalance2())
	fmt.Println(c.AverageBalance3())
}

type Cache struct {
	mu       sync.RWMutex
	balances map[string]float64
}

func (c *Cache) AddBalance(id string, balance float64) {
	c.mu.Lock()
	c.balances[id] = balance
	c.mu.Unlock()
}

func (c *Cache) AverageBalance1() float64 {
	c.mu.RLock()
	// The new variable, whether a map or a slice, is
	// backed by the same data set.
	// same referencing, => data race
	balances := c.balances
	c.mu.RUnlock()

	sum := 0.
	for _, balance := range balances {
		sum += balance
	}
	return sum / float64(len(balances))
}

func (c *Cache) AverageBalance2() float64 {
	c.mu.RLock()
	// If the iteration operation isn’t heavy (that’s the case here, as we perform an incre-
	// ment operation), we should protect the whole function
	defer c.mu.RUnlock()

	sum := 0.
	for _, balance := range c.balances {
		sum += balance
	}
	return sum / float64(len(c.balances))
}

func (c *Cache) AverageBalance3() float64 {
	c.mu.RLock()
	// deep copy
	m := make(map[string]float64, len(c.balances))
	for k, v := range c.balances {
		m[k] = v
	}
	c.mu.RUnlock()

	sum := 0.
	for _, balance := range m {
		sum += balance
	}
	return sum / float64(len(m))
}
