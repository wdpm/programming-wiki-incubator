package main

import "fmt"

// 指针，Go 没有指针运算，稍微简单一点
func main() {
	i, j := 42, 2701

	p := &i         // 指向 i
	fmt.Println(p)  // p指针的值，类似0xc0000140c0
	fmt.Println(*p) // 通过指针读取 i 的值,42
	*p = 21         // 通过指针设置 i 的值
	fmt.Println(i)  // 查看 i 的值,21

	p = &j         // 指向 j
	*p = *p / 37   // 通过指针对 j 进行除法运算，j=73
	fmt.Println(j) // 查看 j 的值
}
