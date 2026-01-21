package main

import "fmt"

type Point struct {
	x int
	y int
}

// 结构体
func main() {
	point := Point{1, 2}
	point.x = 3
	fmt.Println(point)

	fmt.Println(v1, v2, v3, p)
}

var (
	v1 = Point{1, 2}  // 创建一个 Point 类型的结构体
	v2 = Point{x: 1}  // y:0 被隐式地赋予
	v3 = Point{}      // x:0 y:0
	p  = &Point{1, 2} // 创建一个 *Point 类型的结构体（指针）
)

//{3 2}
//{1 2} {1 0} {0 0} &{1 2}
