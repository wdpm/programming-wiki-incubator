package main

import (
	"io"
)

type LowerCaseReader struct {
	reader io.Reader
}

func (l LowerCaseReader) Read(p []byte) (int, error) {
	return 0, nil
}

func foo1(r io.Reader) error {
	b, err := io.ReadAll(r)
	if err != nil {
		return err
	}

	// ...
	_ = b
	return nil
}

func foo2(r io.Reader) error {
	b, err := readAll(r, 3)
	if err != nil {
		return err
	}

	// ...
	_ = b
	return nil
}

func readAll(r io.Reader, retries int) ([]byte, error) {
	b := make([]byte, 0, 512)
	for {
		if len(b) == cap(b) {
			// 这里强制创建一个新的背后array来作为slice，并复制以前的切片数据。
			// 保证b容量足够大
			b = append(b, 0)[:len(b)]
			// 	len= 512 cap = 512x2
			// 	...
		}
		// here: len(b) < cap(b)
		// start index: len(b) 是为了不覆盖之前已读取的任何buffer的数据
		// first turn：0...512 bytes
		// second turn:
		n, err := r.Read(b[len(b):cap(b)])
		// n 包含读入 b 的字节数
		b = b[:len(b)+n]

		if err != nil {
			if err == io.EOF {
				return b, nil
			}
			retries--
			if retries < 0 {
				return b, err
			}
		}
	}
}
