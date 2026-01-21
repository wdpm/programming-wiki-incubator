package main

import "fmt"

// for statement
func main() {
	// 常规for
	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println(sum)

	//无前置后置条件
	a := 0
	for a < 10 {
		a++
	}
	fmt.Println(a)

	// 连分号都去掉
	b := 0
	for b < 10 {
		b++
	}
	fmt.Println(b)
}
