package main

import (
	"fmt"
	"time"
)

type MyError struct {
	When time.Time
	What string
}

func (e *MyError) Error() string {
	return fmt.Sprintf("at %v, %s", e.When, e.What)
}

// error 类型是一个内建接口
// refer src/builtin/builtin.go
func run() error {
	return &MyError{
		time.Now(),
		"it didn't work",
	}
}

func main() {
	// error 为 nil 时表示成功；非 nil 的 error 表示失败。
	if err := run(); err != nil {
		fmt.Println(err)
	}
}
