package main

import "testing"

func BenchmarkPopcnt1(b *testing.B) {
	for i := 0; i < b.N; i++ {
		// inlining optimizations
		popcnt(uint64(i))
	}
}

var global uint64

func BenchmarkPopcnt2(b *testing.B) {
	var v uint64
	for i := 0; i < b.N; i++ {
		// avoid inline optimizations
		v = popcnt(uint64(i))
	}
	global = v
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/11-testing/89-benchmark/compiler-optimiza
// tions
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkPopcnt1
// BenchmarkPopcnt1-8      1000000000               0.3574 ns/op
// BenchmarkPopcnt2
// BenchmarkPopcnt2-8      564232938                2.124 ns/op
// PASS

// Let’s remember the pattern to avoid compiler optimizations fooling benchmark
// results: assign the result of the function under test to a local variable, and then assign
// the latest result to a global variable. This best practice also prevents us from making
// incorrect assumptions
