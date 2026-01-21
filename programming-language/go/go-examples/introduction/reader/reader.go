package main

import (
	"fmt"
	"io"
	"strings"
)

func main() {
	r := strings.NewReader("Hello, Reader!")

	b := make([]byte, 8)
	for {
		// func (r *Reader) Read(b []byte) (n int, err error)
		// Read 用数据填充给定的字节切片并返回填充的字节数和错误值。遇到数据流的结尾时，返回一个 io.EOF
		n, err := r.Read(b)
		fmt.Printf("n = %v err = %v b = %v\n", n, err, b)
		fmt.Printf("b[:n] = %q\n", b[:n])
		if err == io.EOF {
			break
		}
	}
}

//n = 8 err = <nil> b = [72 101 108 108 111 44 32 82]
//b[:n] = "Hello, R"
//n = 6 err = <nil> b = [101 97 100 101 114 33 32 82]
//b[:n] = "eader!"
//n = 0 err = EOF b = [101 97 100 101 114 33 32 82]
//b[:n] = ""
