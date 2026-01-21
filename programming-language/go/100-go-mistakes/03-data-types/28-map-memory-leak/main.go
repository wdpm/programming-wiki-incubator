package main

import (
	"fmt"
	"runtime"
)

func main() {
	// Init
	n := 1_000_000
	// m := make(map[int]*[128]byte)
	m := make(map[int][128]byte)
	printAlloc()

	// Add elements
	for i := 0; i < n; i++ {
		m[i] = randBytes()
	}
	printAlloc()

	// Remove elements
	for i := 0; i < n; i++ {
		delete(m, i)
	}

	// End
	runtime.GC()
	printAlloc()
	// keep map alive
	runtime.KeepAlive(m)
}

func randBytes() [128]byte {
	return [128]byte{}
}

func printAlloc() {
	var m runtime.MemStats
	runtime.ReadMemStats(&m)
	fmt.Printf("%d MB\n", m.Alloc/1024/1024)
}

// 0 MB
// 461 MB
// 293 MB

// In the previous example, we went from 461 MB to 293 MB because the elements
// were collected, but running the GC didn’t impact the map itself.

// use pointers
// 0 MB
// 182 MB
// 38 MB
