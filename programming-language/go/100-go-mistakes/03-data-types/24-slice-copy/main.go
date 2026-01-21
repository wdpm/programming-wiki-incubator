package main

import "fmt"

func bad() {
	src := []int{0, 1, 2}
	var dst []int
	// When using copy, we must recall that the number of elements copied to the destina-
	// tion corresponds to the minimum between the two slices’ lengths.
	copy(dst, src)
	fmt.Println(dst)
	// []

	_ = src
	_ = dst
}

func correct() {
	src := []int{0, 1, 2}
	dst := make([]int, len(src))
	copy(dst, src)
	fmt.Println(dst)

	_ = src
	_ = dst
}
