package main

import "fmt"

// 切片的长度 vs 容量
// 切片的长度: 它所包含的元素个数
// 切片的容量: 从它的第一个元素开始数，到其底层数组元素末尾的个数。
func main() {
	s := []int{2, 3, 5, 7, 11, 13}
	PrintSlice(s)

	// 截取切片使其长度为 0
	s = s[:0]
	PrintSlice(s)

	// 拓展其长度
	s = s[:4]
	PrintSlice(s)

	// 舍弃前两个值
	s = s[2:]
	PrintSlice(s)
}

func PrintSlice(s []int) {
	fmt.Printf("len=%d cap=%d %v\n", len(s), cap(s), s)
}

//len=6 cap=6 [2 3 5 7 11 13] cap的数法：2 3 5 7 11 13
//len=0 cap=6 []              cap的数法：2 3 5 7 11 13
//len=4 cap=6 [2 3 5 7]       cap的数法：2 3 5 7 11 13
//len=2 cap=4 [5 7]           cap的数法：5 7 11 13
