package main

import "fmt"

type Node struct {
	key string
}

type MyInterface interface {
	printKey()
}

// 类型 Node 隐式实现了方法 printKey
func (node Node) printKey() {
	fmt.Println(node.key)
}

func main() {
	node := Node{key: "hello"}
	node.printKey()
}
