package main

import (
	"context"
	"log"
	"time"
)

// may have memory leaks
func consumer1(ch <-chan Event) {
	for {
		select {
		case event := <-ch:
			handle(event)
		// 	As we said, time.After returns a channel. We may expect this channel to be closed
		// during each loop iteration, but this isn’t the case. The resources created by
		// time.After (including the channel) are released once the timeout expires and use
		// memory until that happens.

		// Can we fix this issue by closing the channel programmatically during each itera-
		// tion? No. The returned channel is a <-chan time.Time, meaning it is a receive-only
		// channel that can’t be closed
		case <-time.After(time.Hour):
			log.Println("warning: no messages received")
		}
	}
}

// ok, but isn't the most lightweight solution
func consumer2(ch <-chan Event) {
	for {
		// The downside of this approach is that we have to re-create a context during every sin-
		// gle loop iteration. Creating a context isn’t the most lightweight operation in Go: for
		// example, it requires creating a channel.
		ctx, cancel := context.WithTimeout(context.Background(), time.Hour)
		select {
		case event := <-ch:
			cancel()
			handle(event)
		case <-ctx.Done():
			log.Println("warning: no messages received")
		}
	}
}

func consumer3(ch <-chan Event) {
	timerDuration := 1 * time.Hour
	timer := time.NewTimer(timerDuration)
	// todo stop this timer

	for {
		timer.Reset(timerDuration)
		select {
		case event := <-ch:
			handle(event)
		case <-timer.C:
			log.Println("warning: no messages received")
		}
	}
}

type Event struct{}

func handle(Event) {
}
