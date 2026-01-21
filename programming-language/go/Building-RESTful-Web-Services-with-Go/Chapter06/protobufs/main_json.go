package main

import (
	"fmt"

	"encoding/json"
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
	body, _ := json.Marshal(p)
	fmt.Println(string(body))
}

// {"name":"Roger F","id":1234,"email":"rf@example.com","phones":[{"number":"555-43
// 21","type":1}]}
