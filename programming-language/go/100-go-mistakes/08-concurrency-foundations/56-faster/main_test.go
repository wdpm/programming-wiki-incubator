package main

import (
	"math/rand"
	"testing"
	"time"
)

var global []int

func Benchmark_sequentialMergesort(b *testing.B) {
	var local []int
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		input := getRandomElements()
		b.StartTimer()

		sequentialMergesort(input)
		local = input
	}
	global = local
}

func Benchmark_parallelMergesortV1(b *testing.B) {
	var local []int
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		input := getRandomElements()
		b.StartTimer()

		parallelMergesortV1(input)
		local = input
	}
	global = local
}

func Benchmark_parallelMergesortV2(b *testing.B) {
	var local []int
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		input := getRandomElements()
		b.StartTimer()

		parallelMergesortV2(input)
		local = input
	}
	global = local
}

func getRandomElements() []int {
	n := 10_000
	res := make([]int, n)
	src := rand.NewSource(time.Now().UnixNano())
	rnd := rand.New(src)
	for i := 0; i < n; i++ {
		res[i] = rnd.Int()
	}
	return res
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/08-concurrency-foundations/56-faster
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// Benchmark_sequentialMergesort
// Benchmark_sequentialMergesort-8              769           1323655 ns/op
// Benchmark_parallelMergesortV1
// Benchmark_parallelMergesortV1-8              144           7833250 ns/op
// Benchmark_parallelMergesortV2
// Benchmark_parallelMergesortV2-8             1276            958184 ns/op
// PASS
