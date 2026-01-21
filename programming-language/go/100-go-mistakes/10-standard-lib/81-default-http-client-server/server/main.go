package main

import (
	"net/http"
	"time"
)

func main() {
	s := &http.Server{
		Addr: ":8080",
		// read only the request headers
		ReadHeaderTimeout: 500 * time.Millisecond,
		// read the entire request
		ReadTimeout: 500 * time.Millisecond,
		// for a handler to complete
		Handler: http.TimeoutHandler(handler{}, time.Second, "foo"),

		// Just as we described regarding HTTP clients, on the server side we can configure
		// the maximum amount of time for the next request when keep-alives are enabled.
		// IdleTimeout: 5000,
	}
	_ = s
}

type handler struct{}

func (h handler) ServeHTTP(resp http.ResponseWriter, req *http.Request) {
	// req.Header
}
