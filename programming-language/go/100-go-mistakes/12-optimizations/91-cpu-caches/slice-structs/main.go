package main

type Foo struct {
	a int64
	b int64
}

func sumFoo(foos []Foo) int64 {
	var total int64
	for i := 0; i < len(foos); i++ {
		total += foos[i].a
	}
	return total
}

type Bar struct {
	a []int64
	b []int64
}

func sumBar(bar Bar) int64 {
	var total int64
	for i := 0; i < len(bar.a); i++ {
		total += bar.a[i]
	}
	return total
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/12-optimizations/91-cpu-caches/slice-stru
// cts
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkSumFoo
// BenchmarkSumFoo-8            633           1821214 ns/op
// BenchmarkSumBar
// BenchmarkSumBar-8            782           1290387 ns/op
// PASS
