package main

import (
	"fmt"
	"strconv"
)

type IPAddr [4]byte

// 给 IPAddr 添加一个 "String() string" 方法
// IPAddr{1, 2, 3, 4} 应当打印为 "1.2.3.4
func (ip IPAddr) String() string {
	var result string
	for _, item := range ip {
		part := strconv.Itoa(int(item)) // int -> string
		result += part
		result += "."
	}

	result = result[:len(result)-1]
	return result
}

func main() {
	hosts := map[string]IPAddr{
		"loopback":  {127, 0, 0, 1},
		"googleDNS": {8, 8, 8, 8},
	}
	for name, ip := range hosts {
		fmt.Printf("%v: %v\n", name, ip)
	}
}
