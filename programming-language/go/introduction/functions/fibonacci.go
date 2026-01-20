package main

import "fmt"

// 该闭包返回一个斐波纳契数列 `(0, 1, 1, 2, 3, 5, ...)`。
func fibonacci() func() int {
	var a0 = 0
	var a1 = 1
	return func() int {
		var returnValue = a0

		// iterate in next turn
		var sum = a0 + a1
		a0 = a1
		a1 = sum

		return returnValue
	}
}

func main() {
	f := fibonacci()
	for i := 0; i < 10; i++ {
		fmt.Println(f())
	}
}

//0
//1
//1
//2
//3
//5
//8
//13
//21
//34
