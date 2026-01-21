package main

import "testing"

const n = 1_000_000

var global []Bar

func BenchmarkConvert_EmptySlice(b *testing.B) {
	var local []Bar
	foos := make([]Foo, n)
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		local = convertEmptySlice(foos)
	}
	global = local
}

func BenchmarkConvert_GivenCapacity(b *testing.B) {
	var local []Bar
	foos := make([]Foo, n)
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		local = convertGivenCapacity(foos)
	}
	global = local
}

func BenchmarkConvert_GivenLength(b *testing.B) {
	var local []Bar
	foos := make([]Foo, n)
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		local = convertGivenLength(foos)
	}
	global = local
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/03-data-types/21-slice-init
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkConvert_EmptySlice
// BenchmarkConvert_EmptySlice-8                384           3719491 ns/op
// BenchmarkConvert_GivenCapacity
// BenchmarkConvert_GivenCapacity-8            1351            854595 ns/op
// BenchmarkConvert_GivenLength
// BenchmarkConvert_GivenLength-8              3436            391359 ns/op
// PASS
