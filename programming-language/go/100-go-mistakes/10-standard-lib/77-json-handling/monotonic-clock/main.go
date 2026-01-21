package main

import (
	"encoding/json"
	"fmt"
	"time"
)

type Event struct {
	Time time.Time
}

func listing1() error {
	t := time.Now()
	event1 := Event{
		Time: t,
	}

	b, err := json.Marshal(event1)
	if err != nil {
		return err
	}

	var event2 Event
	err = json.Unmarshal(b, &event2)
	if err != nil {
		return err
	}

	fmt.Println(event1 == event2)
	fmt.Println(event1.Time.Equal(event2.Time))
	return nil
}

func listing2() error {
	t := time.Now()
	event1 := Event{
		// strips the monotonic clock reading in t.
		Time: t.Truncate(0),
	}

	b, err := json.Marshal(event1)
	if err != nil {
		return err
	}

	var event2 Event
	err = json.Unmarshal(b, &event2)
	if err != nil {
		return err
	}

	fmt.Println(event1 == event2)
	return nil
}

func main() {
	listing1()
}
