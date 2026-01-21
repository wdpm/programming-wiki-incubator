package main

import (
	"io"
	"sync"
)

// object pool
var pool = sync.Pool{
	New: func() any {
		return make([]byte, 1024)
	},
}

func write(w io.Writer) {
	buffer := pool.Get().([]byte)
	buffer = buffer[:0] // reset buffer
	defer pool.Put(buffer)

	getResponse(buffer)
	_, _ = w.Write(buffer)
}

func getResponse([]byte) {
}
