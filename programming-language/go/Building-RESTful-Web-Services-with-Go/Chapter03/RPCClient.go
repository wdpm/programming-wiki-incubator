package main

import (
	"log"
	"net/rpc"
)

type Args struct {
}

func main() {
	var reply int64
	args := Args{}
	client, err := rpc.DialHTTP("tcp", "localhost"+":12340")
	if err != nil {
		log.Fatal("dialing:", err)
	}
	err = client.Call("TimeServer.GiveServerTime", args, &reply)
	if err != nil {
		log.Fatal("arith error:", err)
	}
	log.Printf("%d", reply)
}

// 2023/03/05 12:52:10 1677991930
