package main

import (
	"fmt"
	"math"
)

type ErrNegativeSqrt float64

// e变量通过实现Error()的接口成为error类型
// 注意避免死循环：fmt.Sprint(e)时会调用e.Error()来将e转化为字符串类型
// 因此造成循坏调用：fmt.Sprint(e) -> e.Error() -> fmt.Sprint(e) -> ...
// 必须通过将e转换成一个非错误类型(未实现Error接口）的值来避免死循环
func (e ErrNegativeSqrt) Error() string {
	return fmt.Sprint("cannot Sqrt negative number ", float64(e))
}

func Sqrt(f float64) (float64, error) {
	if f < 0 {
		return f, ErrNegativeSqrt(f)
	}
	return math.Sqrt(f), nil
}

func main() {
	fmt.Println(Sqrt(2))
	fmt.Println(Sqrt(-2))
}

//1.4142135623730951 <nil>
//-2 cannot Sqrt negative number -2
