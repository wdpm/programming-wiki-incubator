package main

import "fmt"

// In Go, a slice grows by doubling its size until it contains 1,024 elements, after which it grows by 25%.
func main() {
	s := make([]int, 3, 6)
	print(s)
	// len=3, cap=6: [0 0 0] ? ? ?

	s[1] = 1
	print(s)
	// len=3, cap=6: [0 1 0] ? ? ?

	s = append(s, 2)
	print(s)
	// len=4, cap=6: [0 1 0 2] ? ?

	// cap x 2
	s = append(s, 3, 4, 5)
	print(s)
	// len=7, cap=12: [0 1 0 2 3 4 5] ? ? ? ? ?

	s1 := make([]int, 3, 6)
	// len=3, cap=6: [0 0 0] ? ? ?
	s2 := s1[1:3]
	s1[1] = 1
	print(s2)
	// len=2, cap=5: [1 0] ? ? ?

	s2 = append(s2, 2)
	print(s1)
	// len=3, cap=6: [0 1 0] ? ? ?
	print(s2)
	// len=3, cap=5: [1 0 2] ? ?

	s2 = append(s2, 3)
	s2 = append(s2, 4)
	s2 = append(s2, 5)
	print(s1)
	// len=3, cap=6: [0 1 0] ? ? ?
	print(s2)
	// 一旦扩容， s2背后指向的不再是之前s1所指向的那个array
	// 	len=6, cap=10: [1 0 2 3 4 5] ? ? ? ?
}

func print(s []int) {
	fmt.Printf("len=%d, cap=%d: %v\n", len(s), cap(s), s)
}
