package main

import "fmt"

var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}

func main() {
	// for 循环遍历切片时，每次迭代都会返回两个值。第一个值为当前元素的下标i，第二个值为该下标所对应元素的一份副本v
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}
}
