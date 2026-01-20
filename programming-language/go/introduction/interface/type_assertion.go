package main

import "fmt"

func main() {
	var i interface{} = "hello"

	s := i.(string)
	fmt.Println(s)

	s, ok := i.(string)
	fmt.Println(s, ok)

	//t, ok := i.(T)
	//若 i 保存了一个 T，那么 t 将会是其底层值，而 ok 为 true。
	//否则，ok 将为 false 而 t 将为 T 类型的零值，程序并不会产生恐慌。
	f, ok := i.(float64)
	fmt.Println(f, ok)

	//t := i.(T)
	//该语句断言接口值 i 保存了具体类型 T，并将其底层类型为 T 的值赋予变量 t。
	//若 i 并未保存 T 类型的值，该语句就会触发一个恐慌。
	f = i.(float64) // 报错(panic)
	fmt.Println(f)
}
