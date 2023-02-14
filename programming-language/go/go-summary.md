# summary

## Go highlights

- multi-value returns e.g.(int n, err error)
- goroutines 轻量级用户协程。运行在独立线程或者共享线程，不同 goroutine 之间互相隔离，因为正确性可以保证。
- go channels 协程之间交流值的管道。默认情况下发送和接收阻塞，直到发送者和接收者都准备好。
    - By default channels are unbuffered, meaning that they will only accept sends (chan <-) if there is a corresponding
      receive (<- chan) ready to receive the sent value. Buffered channels accept a limited number of values without a
      corresponding receiver for those values.
    - 单值同步 用 chan
    - 多值同步，推荐用 waitGroup
- select: combine goroutines and go channels. 并发地等待多个goroutine都执行完毕。