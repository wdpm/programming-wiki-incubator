package main

import (
	"net"
	"net/http"
	"time"
)

func main() {
	// By default, the HTTP client does connection pooling. The default
	// client reuses connections (it can be disabled by setting http.Transport.Disable-
	// KeepAlives to true).

	// There’s an extra timeout to specify how long an idle connec-
	// tion is kept in the pool: http.Transport.IdleConnTimeout. The default value is 90
	// seconds, which means the connection can be reused for other requests during this
	// time. After that, if the connection hasn’t been reused, it will be closed.

	// To configure the number of connections in the pool, we must override
	// http.Transport.MaxIdleConns. This value is set to 100 by default. But there’s some-
	// thing important to note: the http.Transport.MaxIdleConnsPerHost limit per host,
	// which by default is set to 2

	client := &http.Client{
		Timeout: 5 * time.Second,
		Transport: &http.Transport{
			DialContext: (&net.Dialer{
				Timeout: time.Second,
			}).DialContext,
			TLSHandshakeTimeout:   time.Second,
			ResponseHeaderTimeout: time.Second,
			// DisableKeepAlives: true,
			// IdleConnTimeout: 90,
			// MaxIdleConnsPerHost: 2,
		},
	}
	_ = client
}
