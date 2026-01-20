package main

import "fmt"

func main() {
	fmt.Println("hello go!")
}

//D:\Code\go-workspace>go install  golang-learning/examples/hello
//
//D:\Code\go-workspace>.\bin\hello.exe
//hello go!

// package path: src/golang-learning/examples/hello

// 生成的二进制文件名是根据 package 名自动生成的（即目录名: hello），和文件名 helloworld.go 毫无关系。
