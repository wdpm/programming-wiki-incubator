package main

import "fmt"

type Person struct {
	Name string
	Age  int
}

func (p Person) String() string {
	return fmt.Sprintf("%v (%v years)", p.Name, p.Age)
}

func main() {
	a := Person{"Arthur Dent", 42}
	z := Person{"Zaphod Beeblebrox", 9001}
	// Stringer 是一个用字符串描述自己的类型。fmt 包通过此接口来打印值。
	// refer /src/fmt/print.go Stringer
	fmt.Println(a, z)
}
