package main

import "fmt"

func main() {
	if 1 < 2 {
		fmt.Println("hello")
	}

	if a := 2; a < 3 {
		fmt.Println("2<3")
	}

	if 1 > 2 {

	} else {
		fmt.Println("1<=2")
	}
}
