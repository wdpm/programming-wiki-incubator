package main

import (
	"context"
	"database/sql"
	"log"
)

func get1(ctx context.Context, db *sql.DB, id string) (string, int, error) {
	rows, err := db.QueryContext(ctx,
		"SELECT DEP, AGE FROM EMP WHERE ID = ?", id)
	if err != nil {
		return "", 0, err
	}
	defer func() {
		err := rows.Close()
		if err != nil {
			log.Printf("failed to close rows: %v\n", err)
		}
	}()

	var (
		department string
		age        int
	)
	for rows.Next() {
		err := rows.Scan(&department, &age)
		if err != nil {
			return "", 0, err
		}
	}

	return department, age, nil
}

func get2(ctx context.Context, db *sql.DB, id string) (string, int, error) {
	rows, err := db.QueryContext(ctx,
		"SELECT DEP, AGE FROM EMP WHERE ID = ?", id)
	if err != nil {
		return "", 0, err
	}
	defer func() {
		err := rows.Close()
		if err != nil {
			log.Printf("failed to close rows: %v\n", err)
		}
	}()

	var (
		department string
		age        int
	)
	for rows.Next() {
		err := rows.Scan(&department, &age)
		if err != nil {
			return "", 0, err
		}
	}
	// because rows.Next can stop either when we
	// have iterated over all the rows or when an error happens while preparing the next
	// row, we should check rows.Err following the iteration
	if err := rows.Err(); err != nil {
		return "", 0, err
	}

	return department, age, nil
}
