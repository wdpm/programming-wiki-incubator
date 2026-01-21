package main

import (
	"fmt"
	restful "github.com/emicklei/go-restful/v3"
	"io"
	"net/http"
	"time"
)

func main() {
	webservice := new(restful.WebService)
	webservice.Route(webservice.GET("/ping").To(pingTime))
	restful.Add(webservice)
	http.ListenAndServe(":8000", nil)
}

func pingTime(req *restful.Request, resp *restful.Response) {
	// 2023-03-05 13:45:31.0569909 +0800 CST m=+52.196326401
	io.WriteString(resp, fmt.Sprintf("%s", time.Now()))
}
