package main

import (
	"fmt"
)

type Vertex struct {
	X, Y float64
}

// 这里为 *Vertex 定义了 Scale 方法
// 指针接收者的方法可以修改接收者指向的值。由于方法经常需要修改它的接收者，指针接收者比值接收者更常用
func (v *Vertex) Scale(f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

// (v Vertex) 为值接收
func (v Vertex) Scale2(f float64) {
	v.X = v.X * f
	v.Y = v.Y * f
}

// 方法的定义: 调用者 方法名 参数 返回值
func main() {
	v := Vertex{3, 4}
	v.Scale(10)
	fmt.Println(v)

	v.Scale2(20)
	fmt.Println(v)
}

//{30 40}
//{30 40}
