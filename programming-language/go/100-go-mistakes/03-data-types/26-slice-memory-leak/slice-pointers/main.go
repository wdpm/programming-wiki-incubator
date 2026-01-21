package main

import (
	"fmt"
	"runtime"
)

type Foo struct {
	v []byte
}

func main() {
	foos := make([]Foo, 1_000)
	printAlloc()

	for i := 0; i < len(foos); i++ {
		foos[i] = Foo{
			v: make([]byte, 1024*1024),
		}
	}
	printAlloc()

	// two := keepFirstTwoElementsOnly(foos)
	// two := keepFirstTwoElementsOnlyCopy(foos)
	two := keepFirstTwoElementsOnlyMarkNil(foos)
	runtime.GC()
	printAlloc()
	runtime.KeepAlive(two)
}

func keepFirstTwoElementsOnly(foos []Foo) []Foo {
	return foos[:2]
}

func keepFirstTwoElementsOnlyCopy(foos []Foo) []Foo {
	// iter 0...1
	res := make([]Foo, 2)
	copy(res, foos)
	return res
}

func keepFirstTwoElementsOnlyMarkNil(foos []Foo) []Foo {
	// iter 2...n
	for i := 2; i < len(foos); i++ {
		foos[i].v = nil
	}
	return foos[:2]
}

func printAlloc() {
	var m runtime.MemStats
	runtime.ReadMemStats(&m)
	fmt.Printf("%d KB\n", m.Alloc/1024)
}

// two := keepFirstTwoElementsOnly(foos)
// 137 KB
// 1024144 KB
// 1024145 KB  => GC not work as expected
