package main

// byte, uint8, int8: 1 byte
// uint16, int16: 2 bytes
// uint32, int32, float32: 4 bytes
// uint64, int64, float64, complex64: 8 bytes
// complex128: 16 bytes

type Foo1 struct {
	b1 byte  // 1 byte
	i  int64 // 8 bytes
	b2 byte  // 1 byte
}

// 24 bytes
type Foo1X struct {
	b1 byte    // 1 byte
	_  [7]byte // add by go compiler
	i  int64   // 8 bytes
	_  [7]byte // add by go compiler
	b2 byte    // 1 byte
}

func sum1(foos []Foo1) int64 {
	var s int64
	for i := 0; i < len(foos); i++ {
		s += foos[i].i
	}
	return s
}

// 16 bytes
type Foo2 struct {
	// sorted by type size in descending order
	i  int64 // 8 bytes
	b1 byte  // 1 byte
	b2 byte  // 1 byte
	// 	padding 6 bytes
}

func sum2(foos []Foo2) int64 {
	var s int64
	for i := 0; i < len(foos); i++ {
		s += foos[i].i
	}
	return s
}

// goos: windows
// goarch: amd64
// pkg: github.com/teivah/100-go-mistakes/12-optimizations/94-data-alignment
// cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
// BenchmarkSum1
// BenchmarkSum1-8              924           1348684 ns/op
// BenchmarkSum2
// BenchmarkSum2-8             1144           1126025 ns/op
// PASS

// 1144/924=1.2380 => 1.24 => 24% faster
