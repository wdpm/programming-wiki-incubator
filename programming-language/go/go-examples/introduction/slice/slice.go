package main

import "fmt"

// slice
func main() {
	primes := [5]int{2, 3, 5, 7, 11}
	var slices []int = primes[1:4] //[1...3] end 4 is exclusive
	fmt.Println(slices)

	// 切片像数组的引用,更改切片就是更改原数组本身
	slices[0] = 90
	fmt.Println(primes)

	// 切片文法类似没有长度的数组
	nums := []int{1, 2, 3, 4, 5}
	fmt.Println(nums)
}

//[3 5 7]
//[2 90 5 7 11]
//[1 2 3 4 5]
