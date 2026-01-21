package main

func listing1() {
	a := 3
	b := 2

	c := sumValue(a, b)
	println(c)
}

// The Go runtime creates a new stack frame as part of the current
// goroutine stack. x and y are allocated alongside z in the current stack frame

//go:noinline
func sumValue(x, y int) int {
	z := x + y
	return z
}

func listing2() {
	a := 3
	b := 2

	// c is on heap, not on stack
	c := sumPtr(a, b)
	println(*c)
}

//go:noinline
func sumPtr(x, y int) *int {
	z := x + y
	return &z
}

// go build -gcflags "-m=2"

func listing3() {
	a := 3
	b := 2
	c := sum(&a, &b)
	println(c)
}

//go:noinline
func sum(x, y *int) int {
	return *x + *y
}
