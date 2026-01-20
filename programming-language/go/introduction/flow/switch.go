package main

import (
	"fmt"
	"runtime"
	"time"
)

// switch
func main() {
	// get os
	// one of darwin, freebsd, linux, and so on.
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X")
	case "freebsd":
		fmt.Println("FreeBSD")
	case "linux":
		fmt.Print("Linux")
	default:
		fmt.Print(os)
	}

	// determinate saturday
	fmt.Println("When's Saturday?")
	today := time.Now().Weekday()
	switch time.Saturday {
	case today + 0:
		fmt.Println("Today.")
	case today + 1:
		fmt.Println("Tomorrow.")
	case today + 2:
		fmt.Println("In two days.")
	default:
		fmt.Println("Too far away.")
	}

	// 无条件的switch
	t := time.Now()
	switch {
	case t.Hour() < 12: //0<= x < 12
		fmt.Println("Good morning!")
	case t.Hour() < 17: //12<= x <17
		fmt.Println("Good afternoon.")
	default: //17<= x < 24
		fmt.Println("Good evening.")
	}
}
