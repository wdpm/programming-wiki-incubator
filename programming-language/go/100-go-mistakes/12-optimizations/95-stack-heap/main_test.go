package main

import "testing"

var (
	globalValue int
	globalPtr   *int
)

func BenchmarkSumValue(b *testing.B) {
	b.ReportAllocs()
	var local int
	for i := 0; i < b.N; i++ {
		local = sumValue(i, i)
	}
	globalValue = local
}

func BenchmarkSumPtr(b *testing.B) {
	b.ReportAllocs()
	var local *int
	for i := 0; i < b.N; i++ {
		local = sumPtr(i, i)
	}
	globalValue = *local
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/12-optimizations/95-stack-heap
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkSumValue
// BenchmarkSumValue-8     813720962                1.407 ns/op           0 B/op
//               0 allocs/op
// BenchmarkSumPtr
// BenchmarkSumPtr-8       73300795                17.33 ns/op            8 B/op
//               1 allocs/op
// PASS
