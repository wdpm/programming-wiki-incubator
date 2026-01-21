package main

import "fmt"

func main() {
	listing1()
	listing2()
	listing3()
}

func listing1() {
	s := []int{1, 2, 3}

	// pass reference
	f(s[:2])
	fmt.Println(s)
}

func listing2() {
	s := []int{1, 2, 3}
	sCopy := make([]int, 2)
	copy(sCopy, s)

	// pass slice copy not reference
	f(sCopy)
	result := append(sCopy, s[2])
	fmt.Println(result)
}

func listing3() {
	s := []int{1, 2, 3}
	// s[low:high:max] capacity = max-low =2-0=2
	// f 里面的append因为容量不够，于是创建新的array，原来的s没有被修改。
	f(s[:2:2])
	fmt.Println(s)
}

func f(s []int) {
	_ = append(s, 10)
}
