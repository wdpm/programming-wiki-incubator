package main

import "fmt"

func main() {
	listing1()
}

func listing1() {
	s := make([]int, 1)

	go func() {
		s1 := append(s, 1)
		fmt.Println(s1)
	}()

	go func() {
		s2 := append(s, 1)
		fmt.Println(s2)
	}()
}

func listing2() {
	// We create a slice with make([]int, 0, 1). Therefore, the array isn’t full. Both goroutines
	// attempt to update the same index of the backing array (index 1), which is a data race.
	s := make([]int, 0, 1)

	go func() {
		sCopy := make([]int, len(s), cap(s))
		copy(sCopy, s)

		s1 := append(sCopy, 1)
		fmt.Println(s1)
	}()

	go func() {
		sCopy := make([]int, len(s), cap(s))
		copy(sCopy, s)

		s2 := append(sCopy, 1)
		fmt.Println(s2)
	}()
}
