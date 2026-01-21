package main

import "fmt"

func main() {
	// 创建字符串切片
	// 其长度和容量都是 5 个元素
	source := []string{"Apple", "Orange", "Plum", "Banana", "Grape"}

	// 对第三个元素做切片，并限制容量
	// 其长度和容量都是 1 个元素
	//slice := source[2:3:3]
	//
	//// 向 slice 追加新字符串
	//slice = append(slice, "Kiwi")
	//
	//fmt.Println(slice)
	//fmt.Println(source)

	slice2 := source[2:3]

	// 向 slice2 追加新字符串
	slice2 = append(slice2, "Kiwi")

	fmt.Println(slice2)

	// source changed
	fmt.Println(source)

	// [Plum Kiwi]
	// [Apple Orange Plum Kiwi Grape]
}
