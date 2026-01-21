package client

import "github.com/teivah/100-go-mistakes/02-code-project-organization/6-interface-producer/store"

// a minimal interface in client side
type customersGetter interface {
	GetAllCustomers() ([]store.Customer, error)
}
