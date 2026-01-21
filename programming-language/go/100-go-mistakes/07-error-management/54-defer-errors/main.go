package main

import (
	"database/sql"
	"log"
)

const query = "..."

func getBalance1(db *sql.DB, clientID string) (float32, error) {
	rows, err := db.Query(query, clientID)
	if err != nil {
		return 0, err
	}
	defer rows.Close()

	// Use rows
	return 0, nil
}

func getBalance2(db *sql.DB, clientID string) (float32, error) {
	rows, err := db.Query(query, clientID)
	if err != nil {
		return 0, err
	}
	// explicitly ignore the result of closing
	defer func() { _ = rows.Close() }()

	// Use rows
	return 0, nil
}

func getBalance3(db *sql.DB, clientID string) (balance float32, err error) {
	rows, err := db.Query(query, clientID)
	if err != nil {
		return 0, err
	}
	// In many cases, you shouldn’t ignore an error returned by a defer function.
	// Either handle it directly or propagate it to the caller, depending on the context.
	// If you want to ignore it, use the blank identifier
	defer func() {
		closeErr := rows.Close()
		if err != nil {
			if closeErr != nil {
				// log scan error
				log.Printf("failed to close rows: %v", err)
			}
			return
		}
		// return close error
		err = closeErr
	}()

	// Use rows
	return 0, nil
}
