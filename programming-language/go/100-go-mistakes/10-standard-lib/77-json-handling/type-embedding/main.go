package main

import (
	"encoding/json"
	"fmt"
	"time"
)

func main() {
	if err := listing1(); err != nil {
		panic(err)
	}
	if err := listing2(); err != nil {
		panic(err)
	}
	if err := listing3(); err != nil {
		panic(err)
	}
}

type Event1 struct {
	ID        int
	time.Time // embedded field
}

func listing1() error {
	event := Event1{
		ID:   1234,
		Time: time.Now(),
	}

	b, err := json.Marshal(event)
	if err != nil {
		return err
	}

	// We have to know that time.Time implements the json.Marshaler interface. Because
	// time.Time is an embedded field of Event, the compiler promotes its methods. There-
	// fore, Event also implements json.Marshaler.

	// Consequently, passing an Event to json.Marshal uses the marshaling behavior
	// provided by time.Time instead of the default behavior. This is why marshaling an
	// Event leads to ignoring the ID field.

	fmt.Println(string(b))
	// "2023-04-12T19:50:48.1334837+08:00"
	return nil
}

type Event2 struct {
	ID   int
	Time time.Time
}

func listing2() error {
	event := Event2{
		ID:   1234,
		Time: time.Now(),
	}

	b, err := json.Marshal(event)
	if err != nil {
		return err
	}

	fmt.Println(string(b))
	return nil
}

type Event3 struct {
	ID int
	time.Time
}

func (e Event3) MarshalJSON() ([]byte, error) {
	return json.Marshal(
		struct {
			ID   int
			Time time.Time
		}{
			ID:   e.ID,
			Time: e.Time,
		},
	)
}

func listing3() error {
	event := Event3{
		ID:   1234,
		Time: time.Now(),
	}

	b, err := json.Marshal(event)
	if err != nil {
		return err
	}

	fmt.Println(string(b))
	return nil
}
