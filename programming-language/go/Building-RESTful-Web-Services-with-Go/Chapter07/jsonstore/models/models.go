package models

import (
	"github.com/jinzhu/gorm"
	_ "github.com/lib/pq"
)

type User struct {
	gorm.Model
	Orders []Order
	Data   string `sql:"type:JSONB NOT NULL DEFAULT '{}'::JSONB" json:"-"`
}

type Order struct {
	gorm.Model
	User User
	Data string `sql:"type:JSONB NOT NULL DEFAULT '{}'::JSONB"`
}

// TableName GORM creates tables with plural names. Use this to suppress it
func (User) TableName() string {
	return "user"
}

func (Order) TableName() string {
	return "order"
}

func InitDB() (*gorm.DB, error) {
	var err error
	db, err := gorm.Open("postgres", "postgres://wdpm:123456@172.20.218.221/mydb?sslmode=disable")
	if err != nil {
		return nil, err
	} else {
		/*
			// The below AutoMigrate is equivalent to this
			if !db.HasTable("user") {
				db.CreateTable(&User{})
			}

			if !db.HasTable("order") {
				db.CreateTable(&Order{})
			}
		*/
		db.AutoMigrate(&User{}, &Order{})
		return db, nil
	}
}
