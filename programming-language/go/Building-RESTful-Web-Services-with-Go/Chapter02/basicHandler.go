package main

import (
	"io"
	"log"
	"net/http"
)

// MyServer hello world, the web server
func MyServer(w http.ResponseWriter, req *http.Request) {
	_, err := io.WriteString(w, "hello, world!\n")
	if err != nil {
		log.Println(err)
	}
}

func main() {
	http.HandleFunc("/hello", MyServer)
	log.Fatal(http.ListenAndServe(":8000", nil))
}
