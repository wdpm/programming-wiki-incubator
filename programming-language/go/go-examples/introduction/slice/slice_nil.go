package main

import "fmt"

// slice的零值
func main() {
	var s []int
	fmt.Println(s, len(s), cap(s))
	if s == nil {
		fmt.Println("nil!")
	}
}

//[] 0 0
//nil!
