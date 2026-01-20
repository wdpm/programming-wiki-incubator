package main

import "fmt"

// [函数定义]
func main() {
	fmt.Println(add(2, 3))

	fmt.Println(multiply(2, 4))
}

// add：方法名
// x int, y int：形参变量x，变量类型int；形参变量y，变量类型int
// 末尾的 int：返回类型
func add(x int, y int) int {
	return x + y
}

// (x, y int) 为缩写形式，x和y都为int类型
func multiply(x, y int) int {
	return x * y
}
