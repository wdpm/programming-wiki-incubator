package main

import "sync"

type Input struct {
	a int64
	b int64
}

// When a cache line is shared across multiple cores and at least one goroutine is a
// writer, the entire cache line is invalidated. This happens even if the updates are logi-
// cally independent (for example, sumA and sumB). This is the problem of false sharing,
// and it degrades performance.

// Internally, a CPU uses the MESI protocol to guarantee cache coher-
// ency. It tracks each cache line, marking it modified, exclusive, shared, or
// invalid (MESI).
type Result1 struct {
	// In this example, sumA and sumB are
	// part of the same memory block
	sumA int64
	sumB int64
}

func count1(inputs []Input) Result1 {
	wg := sync.WaitGroup{}
	wg.Add(2)

	result := Result1{}

	go func() {
		for i := 0; i < len(inputs); i++ {
			result.sumA += inputs[i].a
		}
		wg.Done()
	}()

	go func() {
		for i := 0; i < len(inputs); i++ {
			result.sumB += inputs[i].b
		}
		wg.Done()
	}()

	wg.Wait()
	return result
}

type Result2 struct {
	sumA int64
	// here
	// Because an int64 requires an 8-byte allocation and a cache line 64 bytes long, we
	// need 64 – 8 = 56 bytes of padding:
	// 这里为了内存对齐，补了56凑合（这56bytes的代价是不是太大了？）。
	// 弊端很明显，空间增大了。这是典型的空间换时间策略。（时空权衡）
	_    [56]byte
	sumB int64
}

func count2(inputs []Input) Result2 {
	wg := sync.WaitGroup{}
	wg.Add(2)

	result := Result2{}

	go func() {
		for i := 0; i < len(inputs); i++ {
			result.sumA += inputs[i].a
		}
		wg.Done()
	}()

	go func() {
		for i := 0; i < len(inputs); i++ {
			result.sumB += inputs[i].b
		}
		wg.Done()
	}()

	wg.Wait()
	return result
}
