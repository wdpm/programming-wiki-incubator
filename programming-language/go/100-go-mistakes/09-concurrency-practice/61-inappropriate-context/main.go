package main

import (
	"context"
	"net/http"
	"time"
)

func handler1(w http.ResponseWriter, r *http.Request) {
	response, err := doSomeTask(r.Context(), r)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	go func() {
		// propagate the parent context.
		// Problem: when response write back is finished, r.Context() has been cancelled.
		err := publish(r.Context(), response)
		// Do something with err
		_ = err
	}()

	writeResponse(response)
}

func handler2(w http.ResponseWriter, r *http.Request) {
	response, err := doSomeTask(r.Context(), r)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	go func() {
		// One idea is to not propagate the parent context.
		// Instead, we would call publish with an empty context
		// Problem: But what if the context contained useful values?
		err := publish(context.Background(), response)
		// Do something with err
		_ = err
	}()

	writeResponse(response)
}

func handler3(w http.ResponseWriter, r *http.Request) {
	response, err := doSomeTask(r.Context(), r)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	go func() {
		// Now the context passed to publish will never expire or be canceled, but it will carry
		// the parent context’s values
		// 这里思路就是复制一份原来http的context，然后提供给消息中间件使用
		err := publish(detach{ctx: r.Context()}, response)
		// Do something with err
		_ = err
	}()

	writeResponse(response)
}

// Because the context is canceled once we return
// the response, the asynchronous action can also be stopped unexpectedly.So we need to create our own context
type detach struct {
	ctx context.Context
}

func (d detach) Deadline() (time.Time, bool) {
	return time.Time{}, false
}

func (d detach) Done() <-chan struct{} {
	return nil
}

func (d detach) Err() error {
	return nil
}

func (d detach) Value(key any) any {
	return d.ctx.Value(key)
}

func doSomeTask(context.Context, *http.Request) (string, error) {
	return "", nil
}

func publish(context.Context, string) error {
	return nil
}

func writeResponse(string) {}
