package main

import "fmt"

const (
	StatusSuccess  = "success"
	StatusErrorFoo = "error_foo"
	StatusErrorBar = "error_bar"
)

func main() {
	_ = f1()
	_ = f2()
	_ = f3()

	testClosure()
}

func testClosure() {
	i := 0
	j := 0
	defer func(i int) {
		fmt.Println(i, j)
		// 	0 1
	}(i)
	i++
	j++
}

func f1() error {
	var status string
	defer notify(status)
	defer incrementCounter(status)

	if err := foo(); err != nil {
		status = StatusErrorFoo
		return err
	}

	if err := bar(); err != nil {
		status = StatusErrorBar
		return err
	}

	status = StatusSuccess
	return nil
}

func f2() error {
	var status string
	// status itself is modified throughout the function, but its address remains constant,
	// regardless of the assignments.
	defer notifyPtr(&status)
	defer incrementCounterPtr(&status)

	if err := foo(); err != nil {
		status = StatusErrorFoo
		return err
	}

	if err := bar(); err != nil {
		status = StatusErrorBar
		return err
	}

	status = StatusSuccess
	return nil
}

func f3() error {
	var status string
	defer func() {
		// Therefore, status is
		// evaluated once the closure is executed, not when we call defer
		notify(status)
		incrementCounter(status)
	}()

	if err := foo(); err != nil {
		status = StatusErrorFoo
		return err
	}

	if err := bar(); err != nil {
		status = StatusErrorBar
		return err
	}

	status = StatusSuccess
	return nil
}

func notify(status string) {
	fmt.Println("notify:", status)
}

func incrementCounter(status string) {
	fmt.Println("increment:", status)
}

func notifyPtr(status *string) {
	fmt.Println("notify:", *status)
}

func incrementCounterPtr(status *string) {
	fmt.Println("increment:", *status)
}

func foo() error {
	return nil
}

func bar() error {
	return nil
}
