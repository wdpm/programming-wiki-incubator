package main

import "fmt"

func main() {
	s := Struct{id: "foo"}
	defer s.print()
	// foo
	s.id = "bar"
}

type Struct struct {
	id string
}

func (s Struct) print() {
	fmt.Println(s.id)
}

// hence, the behavior depends on whether the receiver is a value or a pointer.
