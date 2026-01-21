package main

import (
	"log"
	"net/http"
)

func listing1() error {
	var client *http.Client
	if tracing {
		// variable shadow
		client, err := createClientWithTracing()
		if err != nil {
			return err
		}
		log.Println(client)
	} else {
		client, err := createDefaultClient()
		if err != nil {
			return err
		}
		log.Println(client)
	}

	_ = client
	return nil
}

func listing2() error {
	var client *http.Client
	if tracing {
		c, err := createClientWithTracing()
		if err != nil {
			return err
		}
		// assign temp var to outer var
		client = c
	} else {
		c, err := createDefaultClient()
		if err != nil {
			return err
		}
		client = c
	}

	_ = client
	return nil
}

func listing3() error {
	// define two vars
	var client *http.Client
	var err error
	if tracing {
		// assignment
		client, err = createClientWithTracing()
		if err != nil {
			return err
		}
	} else {
		client, err = createDefaultClient()
		if err != nil {
			return err
		}
	}

	_ = client
	return nil
}

func listing4() error {
	var client *http.Client
	var err error
	if tracing {
		client, err = createClientWithTracing()
	} else {
		client, err = createDefaultClient()
	}

	// 这里对比listing 3 将公有的代码块提取出来
	if err != nil {
		return err
	}

	_ = client
	return nil
}

var tracing bool

func createClientWithTracing() (*http.Client, error) {
	return nil, nil
}

func createDefaultClient() (*http.Client, error) {
	return nil, nil
}
