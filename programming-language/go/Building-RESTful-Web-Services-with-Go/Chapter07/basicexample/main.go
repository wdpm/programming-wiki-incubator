package main

import (
	"github.com/narenaryan/models"
	"log"
)

func main() {
	db, err := models.InitDB()
	if err != nil {
		log.Println(db)
	}
}
