package main

import "fmt"

func main() {
	// map (string,int)
	m := make(map[string]int)

	m["Answer"] = 42
	fmt.Println("The value:", m["Answer"])

	m["Answer"] = 48
	fmt.Println("The value:", m["Answer"])

	delete(m, "Answer")
	fmt.Println("The value:", m["Answer"])

	//若 key 在 m 中，ok 为 true ；否则，ok 为 false。
	//若 key 不在 m 中，那么 elem 是该映射元素类型的零值。
	v, ok := m["Answer"]
	fmt.Println("The value:", v, "Present?", ok)
}
