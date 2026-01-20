package main

import "fmt"

func main() {
	fmt.Println(split(17))
}

// [函数命名的返回值] x and y
func split(sum int) (x int, y int) {
	x = sum + 1
	y = sum + 2
	return
}
