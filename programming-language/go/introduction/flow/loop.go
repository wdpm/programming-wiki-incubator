package main

import (
	"fmt"
	"math"
)

func main() {
	fmt.Println(sqrt(2))
}

func sqrt(x int) float64 {
	//z -= (z*z - x) / (2*z)
	//z² − x 是 z² 到 x 的距离， 除以的 2z 为 z² 的导数，通过 z² 的变化速度来改变 z 的调整量。牛顿法。

	z := 1.0
	var delta = 0.000001
	// 当 z² − x 很小时，认为精度已经足够，退出for
	for math.Abs(z*z-float64(x)) > delta {
		z -= (z*z - float64(x)) / (2 * z)
		fmt.Println(z)
	}

	return z
}
