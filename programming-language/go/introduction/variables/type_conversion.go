package main

import (
	"fmt"
	"math"
)

// 类型转换，例如 float64(x*x + y*y) uint(f)
func main() {
	var x, y int = 3, 4
	var f = math.Sqrt(float64(x*x + y*y))
	var z = uint(f)
	fmt.Println(x, y, z)
}
