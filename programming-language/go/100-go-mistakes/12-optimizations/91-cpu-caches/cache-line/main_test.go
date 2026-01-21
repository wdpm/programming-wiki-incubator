package main

import "testing"

var global int64

func BenchmarkSum2(b *testing.B) {
	var local int64
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		s := make([]int64, 1_000_000)
		b.StartTimer()
		local = sum2(s)
	}
	global = local
}

// only 1.2X facter than sum2 due to CPU cache line
func BenchmarkSum8(b *testing.B) {
	var local int64
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		s := make([]int64, 1_000_000)
		b.StartTimer()
		local = sum8(s)
	}
	global = local
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/12-optimizations/91-cpu-caches/cache-line
//
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkSum2
// BenchmarkSum2-8              928           1099820 ns/op
// BenchmarkSum8
// BenchmarkSum8-8             1424            889836 ns/op
// PASS
