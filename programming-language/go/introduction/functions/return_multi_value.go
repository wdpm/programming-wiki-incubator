package main

import "fmt"

func main() {
	s1, s2 := swap("hello", "world")
	fmt.Println(s1, s2)
}

// [函数多值返回]
func swap(x string, y string) (string, string) {
	return y, x
}
