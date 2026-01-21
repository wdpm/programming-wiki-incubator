package main

import (
	base62 "github.com/narenaryan/base62"
	"log"
)

func main() {
	x := 100
	base62String := base62.ToBase62(x)
	log.Println(base62String)
	normalNumber := base62.ToBase10(base62String)
	log.Println(normalNumber)
}

// 2023/03/07 22:20:48 1C = 1 * (62 ^1) + C * (62 ^0)
// 2023/03/07 22:20:48 100
