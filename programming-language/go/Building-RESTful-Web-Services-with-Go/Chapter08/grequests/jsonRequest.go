package main

import (
	"github.com/levigross/grequests"
	"log"
)

func main() {
	resp, err := grequests.Get("http://httpbin.org/get", nil)
	// You can modify the request by passing an optional RequestOptions struct
	if err != nil {
		log.Fatalln("Unable to make request: ", err)
	}
	var returnData map[string]interface{}
	resp.JSON(&returnData)
	log.Println(returnData)

}

// 2023/03/09 21:02:42 map[args:map[] headers:map[Accept-Encoding:gzip Host:httpbin.org User-Agent:GRequests/0.10
// X-Amzn-Trace-Id:Root=1-6409d8f6-3995220c1dcd569009d4d65a] origin:13.229.234.194 url:http://httpbin.org/get]
