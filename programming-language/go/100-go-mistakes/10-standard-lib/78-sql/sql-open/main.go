package main

import "database/sql"

var dsn = ""

func listing1() error {
	// Open may just validate its arguments without creating a connection to the database
	db, err := sql.Open("mysql", dsn)
	if err != nil {
		return err
	}

	// Note that an alternative to Ping is PingContext,
	// which asks for an additional context conveying when the ping should be canceled or
	// time out.
	if err := db.Ping(); err != nil {
		return err
	}

	_ = db
	return nil
}
