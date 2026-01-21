package main

func convertEmptySlice(foos []Foo) []Bar {
	// 会造成每次都allocate一个新的backed array，非常低效
	bars := make([]Bar, 0)

	for _, foo := range foos {
		bars = append(bars, fooToBar(foo))
	}
	return bars
}

func convertGivenCapacity(foos []Foo) []Bar {
	n := len(foos)
	// 提前分配足够的N容量，避免每次append时创建新的背后array，效率略低于length，但是代码可读性有时很好
	bars := make([]Bar, 0, n)

	for _, foo := range foos {
		bars = append(bars, fooToBar(foo))
	}
	return bars
}

func convertGivenLength(foos []Foo) []Bar {
	n := len(foos)
	// to allocate bars with a given length: 效率一般是最好
	bars := make([]Bar, n)

	for i, foo := range foos {
		bars[i] = fooToBar(foo)
	}
	return bars
}

type Foo struct{}

type Bar struct{}

func fooToBar(foo Foo) Bar {
	return Bar{}
}
