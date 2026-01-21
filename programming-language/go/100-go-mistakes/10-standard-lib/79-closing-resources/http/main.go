package main

import (
	"io"
	"log"
	"net/http"
)

func (h handler) getBody1() (string, error) {
	resp, err := h.client.Get(h.url)
	if err != nil {
		return "", err
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return "", err
	}

	return string(body), nil
}

func (h handler) getBody2() (string, error) {
	resp, err := h.client.Get(h.url)
	if err != nil {
		return "", err
	}

	body, err := io.ReadAll(resp.Body)
	if err != nil {
		return "", err
	}

	defer func() {
		// GC
		// On the server side, while implementing an HTTP handler, we aren’t
		// required to close the request body because the server does this automatically.
		err := resp.Body.Close()
		if err != nil {
			log.Printf("failed to close response: %v\n", err)
		}
	}()

	return string(body), nil
}

func (h handler) getStatusCode1(body io.Reader) (int, error) {
	resp, err := h.client.Post(h.url, "application/json", body)
	if err != nil {
		return 0, err
	}

	defer func() {
		// close body event if we don't read body
		err := resp.Body.Close()
		if err != nil {
			log.Printf("failed to close response: %v\n", err)
		}
	}()

	return resp.StatusCode, nil
}

func (h handler) getStatusCode2(body io.Reader) (int, error) {
	resp, err := h.client.Post(h.url, "application/json", body)
	if err != nil {
		return 0, err
	}

	defer func() {
		err := resp.Body.Close()
		if err != nil {
			log.Printf("failed to close response: %v\n", err)
		}
	}()

	// Therefore, if getStatusCode is called repeatedly and we want to use keep-alive con-
	// nections, we should read the body even though we aren’t interested in it
	_, _ = io.Copy(io.Discard, resp.Body)

	return resp.StatusCode, nil
}

type handler struct {
	client http.Client
	url    string
}
