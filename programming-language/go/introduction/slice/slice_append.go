package main

import "fmt"

// slice append
func main() {
	var s []int
	printSlice2(s)

	// 向一个空切片添加0
	s = append(s, 0)
	printSlice2(s)

	// 切片按需增长
	s = append(s, 1)
	printSlice2(s)

	// 可以一次性添加多个元素
	// 当 s 的底层数组太小，不足以容纳所有给定的值时，分配一个更大的数组。返回的切片会指向这个新分配的数组。
	s = append(s, 2, 3, 4)
	printSlice2(s)
}

func printSlice2(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

//len=0 cap=0 []
//len=1 cap=1 [0]
//len=2 cap=2 [0 1]
//len=5 cap=6 [0 1 2 3 4] 注意这里的cap=6
