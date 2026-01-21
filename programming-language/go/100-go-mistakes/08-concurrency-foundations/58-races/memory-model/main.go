package main

import "fmt"

func listing1() {
	i := 0
	go func() {
		i++
	}()
}

func listing2() {
	i := 0
	go func() {
		i++
	}()
	fmt.Println(i)
}

// variable increment < channel send < channel receive < variable read
func listing3() {
	i := 0
	ch := make(chan struct{})
	go func() {
		<-ch
		fmt.Println(i)
	}()
	i++
	ch <- struct{}{}
}

func listing4() {
	i := 0
	ch := make(chan struct{})
	go func() {
		<-ch
		fmt.Println(i)
	}()
	i++
	close(ch)
}

func listing5() {
	i := 0
	// use buffered channel
	ch := make(chan struct{}, 1)
	go func() {
		i = 1
		<-ch
	}()
	ch <- struct{}{}
	fmt.Println(i)
	// 0
}

func listing6() {
	i := 0
	ch := make(chan struct{})
	go func() {
		i = 1
		<-ch
	}()
	ch <- struct{}{}
	fmt.Println(i)
	// 1
}

func main() {
	listing5()
	// listing6()
}
