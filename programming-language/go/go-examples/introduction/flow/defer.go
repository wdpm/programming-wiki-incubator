package main

import "fmt"

func main() {
	//defer 将函数推迟到外层函数返回之后执行。
	//推迟调用的函数其参数会立即求值，但直到外层函数返回前该函数都不会被调用。
	defer fmt.Println("defer")
	fmt.Println("No defer")

	fmt.Println("counting")
	// 入栈顺序：0,1,2,3,...,9
	for i := 0; i < 10; i++ {
		defer fmt.Println(i)
	}
	fmt.Println("done")
}

//No defer
//counting
//done
//9
//8
//7
//6
//5
//4
//3
//2
//1
//0
//defer
