package main

import "testing"

var global [2]int64

func BenchmarkAdd(b *testing.B) {
	a := [2]int64{}
	var local [2]int64
	for i := 0; i < b.N; i++ {
		local = add(a)
	}
	global = local
}

func BenchmarkAdd2(b *testing.B) {
	a := [2]int64{}
	var local [2]int64
	for i := 0; i < b.N; i++ {
		local = add2(a)
	}
	global = local
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/12-optimizations/93-instruction-level-par
// allelism
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkAdd
// BenchmarkAdd-8               757           1659168 ns/op
// BenchmarkAdd2
// BenchmarkAdd2-8              904           1315713 ns/op
// PASS

// 904/757= 1.1941 => 20% faster
