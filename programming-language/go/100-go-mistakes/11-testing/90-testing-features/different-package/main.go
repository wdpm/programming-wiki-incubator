package counter

import "sync/atomic"

// unexported
var count uint64

func Inc() uint64 {
	atomic.AddUint64(&count, 1)
	return count
}
