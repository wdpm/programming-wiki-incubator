package main

import (
	"database/sql"
	"os"
	"testing"
)

func TestMySQLIntegration(t *testing.T) {
	setupMySQL()
	defer teardownMySQL()

	// createConnection()

	// ...
}

func createConnection(t *testing.T, dsn string) *sql.DB {
	db, err := sql.Open("mysql", dsn)
	if err != nil {
		t.FailNow()
	}
	// remember to GC db pool
	t.Cleanup(
		func() {
			_ = db.Close()
		})
	return db
}

func TestMain(m *testing.M) {
	// os.Exit(m.Run())

	setupMySQL()
	code := m.Run()
	teardownMySQL()
	os.Exit(code)
}

func setupMySQL() {}

func teardownMySQL() {}
