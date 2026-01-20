package main

import "golang.org/x/tour/reader"

// 实现一个 Reader 类型，产生一个 ASCII 字符 'A' 的无限流。
type MyReader struct{}

// 给 MyReader 添加一个 Read(b []byte) (int, error) 方法
func (r MyReader) Read(b []byte) (int, error) {
	b[0] = 'A'
	return 1, nil
}

func main() {
	reader.Validate(MyReader{})
}

//OK!
