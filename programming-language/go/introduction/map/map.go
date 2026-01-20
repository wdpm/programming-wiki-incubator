package main

import "fmt"

type Vertex struct {
	Lat, Long float64
}

var m map[string]Vertex

// map
func main() {
	// make 函数返回给定类型的映射，并将其初始化备用
	m = make(map[string]Vertex)
	m["Bell Labs"] = Vertex{
		40.68433, -74.39967,
	}
	fmt.Println(m["Bell Labs"])
}

//{40.68433 -74.39967}
