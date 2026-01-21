package main

import (
	"github.com/julienschmidt/httprouter"
	"log"
	"net/http"
)

func main() {
	router := httprouter.New()
	// Mapping to methods is possible with HttpRouter
	// GET http://localhost:8000/static/latin.txt
	router.ServeFiles("/static/*filepath", http.Dir("./"))
	log.Fatal(http.ListenAndServe(":8000", router))
}
