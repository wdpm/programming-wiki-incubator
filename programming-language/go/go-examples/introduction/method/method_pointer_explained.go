package main

import (
	"fmt"
	"math"
)

type Vertex2 struct {
	X, Y float64
}

func Abs(v Vertex2) float64 {
	return math.Sqrt(v.X*v.X + v.Y*v.Y)
}

// 带指针参数的函数必须接受一个指针
// 调用形式 Scale(&v, 10)
func Scale(v *Vertex2, f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

//把 Abs 和 Scale 方法重写为函数。
func main() {
	v := Vertex2{3, 4}
	Scale(&v, 10)
	fmt.Println(v)
}
