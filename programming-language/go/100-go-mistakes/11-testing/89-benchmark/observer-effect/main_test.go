package main

import "testing"

const rows = 1000

var res int64

func BenchmarkCalculateSum512_1(b *testing.B) {
	var sum int64
	// use CPU caches
	s := createMatrix512(rows)
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		sum = calculateSum512(s)
	}
	res = sum
}

func BenchmarkCalculateSum513_1(b *testing.B) {
	var sum int64
	// use CPU caches
	s := createMatrix513(rows)
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		sum = calculateSum513(s)
	}
	res = sum
}

func BenchmarkCalculateSum512_2(b *testing.B) {
	var sum int64
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		// not use CPU caches
		s := createMatrix512(rows)
		b.StartTimer()
		sum = calculateSum512(s)
	}
	res = sum
}

func BenchmarkCalculateSum513_2(b *testing.B) {
	var sum int64
	for i := 0; i < b.N; i++ {
		b.StopTimer()
		s := createMatrix512(rows)
		b.StartTimer()
		sum = calculateSum512(s)
	}
	res = sum
}

func createMatrix512(r int) [][512]int64 {
	return make([][512]int64, r)
}

func createMatrix513(r int) [][513]int64 {
	return make([][513]int64, r)
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/11-testing/89-benchmark/observer-effect
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkCalculateSum512_1
// BenchmarkCalculateSum512_1-8      117507             11225 ns/op
// BenchmarkCalculateSum513_1
// BenchmarkCalculateSum513_1-8      154932              7542 ns/op => 不要被CPU忽悠了，这个方法并不快
// BenchmarkCalculateSum512_2
// BenchmarkCalculateSum512_2-8       66331             17753 ns/op
// BenchmarkCalculateSum513_2
// BenchmarkCalculateSum513_2-8       90852             20282 ns/op => 看吧，去掉CPU缓存后，原型毕露了
// PASS
