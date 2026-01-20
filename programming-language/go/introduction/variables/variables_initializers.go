package main

import "fmt"

//[变量初始化声明]
var i, j int = 1, 2

func main() {
	var a, b, c = "hello", true, 123
	fmt.Println(i, j)
	fmt.Println(a, b, c)
}
