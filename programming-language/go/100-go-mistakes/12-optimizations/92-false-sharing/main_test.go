package main

import "testing"

const n = 1_000_000

var globalResult1 Result1

func BenchmarkCount1(b *testing.B) {
	var local Result1
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		inputs := make([]Input, n)
		b.StartTimer()
		local = count1(inputs)
	}
	globalResult1 = local
}

var globalResult2 Result2

func BenchmarkCount2(b *testing.B) {
	var local Result2
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		inputs := make([]Input, n)
		b.StartTimer()
		local = count2(inputs)
	}
	globalResult2 = local
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/12-optimizations/92-false-sharing
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkCount1
// BenchmarkCount1-8            250           4843512 ns/op
// BenchmarkCount2
// BenchmarkCount2-8            343           3106608 ns/op
// PASS

// 343/250=1.372 => 40% faster
