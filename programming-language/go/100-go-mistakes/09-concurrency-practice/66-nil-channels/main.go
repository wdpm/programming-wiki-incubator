package main

func merge1(ch1, ch2 <-chan int) <-chan int {
	ch := make(chan int, 1)

	go func() {
		for v := range ch1 {
			ch <- v
		}
		// 弊端：ch2需要等待ch1取完一切元素才能进行
		for v := range ch2 {
			ch <- v
		}
		close(ch)
	}()

	return ch
}

func merge2(ch1, ch2 <-chan int) <-chan int {
	ch := make(chan int, 1)

	go func() {
		for {
			select {
			// 使用select均衡选取case
			case v := <-ch1:
				ch <- v
			case v := <-ch2:
				ch <- v
			}
		}
		// 该函数中的close语句不可达，因为上面的循环一直运行，并且没有任何终止循环的条件
		close(ch)
	}()

	return ch
}

func merge3(ch1, ch2 <-chan int) <-chan int {
	ch := make(chan int, 1)
	// 通过两个bool变量来标记这两个ch是否分别关闭
	ch1Closed := false
	ch2Closed := false

	go func() {
		for {
			//  Let’s say ch1 is closed (so we won’t receive any new
			// messages here); when we reach select again, it will wait for one of these three condi-
			// tions to happen:
			// - ch1 is closed.
			// - ch2 has a new message.
			// - ch2 is closed
			// The first condition, ch1 is closed, will always be valid. Therefore, as long as we don’t
			// receive a message in ch2 and this channel isn’t closed, we will keep looping over the
			// first case. This will lead to wasting CPU cycles and must be avoided. Therefore, our
			// solution isn’t viable
			select {
			case v, open := <-ch1:
				if !open {
					ch1Closed = true
					break
				}
				ch <- v
			case v, open := <-ch2:
				if !open {
					ch2Closed = true
					break
				}
				ch <- v
			}

			if ch1Closed && ch2Closed {
				close(ch)
				return
			}
		}
	}()

	return ch
}

func merge4(ch1, ch2 <-chan int) <-chan int {
	ch := make(chan int, 1)

	go func() {
		for ch1 != nil || ch2 != nil {
			select {
			// we can use nil channels to implement an elegant state
			// machine that will remove one case from a select statement
			// 	一旦ch1 = nil,下次select时不会再进入第一个case
			case v, open := <-ch1:
				if !open {
					// 显式GC，置nil
					ch1 = nil
					break
				}
				ch <- v
			case v, open := <-ch2:
				if !open {
					// 显式GC，置nil
					ch2 = nil
					break
				}
				ch <- v
			}
		}
		close(ch)
	}()

	return ch
}
