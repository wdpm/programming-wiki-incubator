package main

import "fmt"

//bool

//string

//int  int8  int16  int32  int64
//uint uint8 uint16 uint32 uint64 uintptr

//byte // uint8 的别名

//rune // int32 的别名
// 表示一个 Unicode 码点

//float32 float64

//complex64 complex128

// [基本类型]
// 零值（默认值）： 数值类型为 0， 布尔类型为 false， 字符串为 ""（空字符串）
func main() {

	// 基本类型默认值

	var a bool
	fmt.Println(a)

	var str string
	fmt.Println(str)

	var num1 int
	var num2 int8
	var num3 int16
	var num4 int32
	var num5 int64
	fmt.Println(num1, num2, num3, num4, num5)

	var num6 uint
	var num7 uint8
	var num8 uint16
	var num9 uint32
	var num10 uint64
	var num11 uintptr
	fmt.Println(num6, num7, num8, num9, num10, num11)

	var r rune
	fmt.Println(r)

	var f1 float32
	var f2 float64
	fmt.Println(f1, f2)

	var c1 complex64
	var c2 complex128
	fmt.Println(c1, c2)

	// 赋值
	var c3 complex64 = 1 + 2i
	fmt.Println(c3)

}
