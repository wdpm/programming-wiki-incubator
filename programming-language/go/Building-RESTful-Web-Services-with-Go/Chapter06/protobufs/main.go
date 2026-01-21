package main

import (
	"fmt"

	"github.com/golang/protobuf/proto"
	pb "github.com/narenaryan/protofiles"
)

func main() {
	p := &pb.Person{
		Id:    1234,
		Name:  "Roger F",
		Email: "rf@example.com",
		Phones: []*pb.Person_PhoneNumber{
			{Number: "555-4321", Type: pb.Person_HOME},
		},
	}

	p1 := &pb.Person{}
	body, _ := proto.Marshal(p)
	_ = proto.Unmarshal(body, p1)
	fmt.Println("Original struct loaded from proto file:", p, "\n")
	fmt.Println("Marshalled proto data: ", body, "\n")
	fmt.Println("Unmarshalled struct: ", p1)
}

// Original struct loaded from proto file: name:"Roger F"  id:1234  email:"rf@examp
// le.com"  phones:{number:"555-4321"  type:HOME}
//
// Marshalled proto data:  [10 7 82 111 103 101 114 32 70 16 210 9 26 14 114 102 64
// 101 120 97 109 112 108 101 46 99 111 109 34 12 10 8 53 53 53 45 52 51 50 49 16
// 1]
//
// Unmarshalled struct:  name:"Roger F"  id:1234  email:"rf@example.com"  phones:{n
// umber:"555-4321"  type:HOME}