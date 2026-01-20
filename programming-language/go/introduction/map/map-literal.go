package main

import "fmt"

type Vertex2 struct {
	Lat, Long float64
}

// key -> struct object
var m1 = map[string]Vertex2{
	"Bell Labs": {
		40.68433, -74.39967,
	},
	"Google": {
		37.42202, -122.08408,
	},
}

func main() {
	fmt.Println(m1)
}

//map[Bell Labs:{40.68433 -74.39967} Google:{37.42202 -122.08408}]
