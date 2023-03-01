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

### 接口方法：调用者为值还是指针？

```go
func (m defaultMatcher) Search
```

如果声明函数的时候带有接收者，则意味着声明了一个方法。这个方法会和指定的接收者的
类型绑在一起。在我们的例子里，Search 方法与 defaultMatcher 类型的值绑在一起。这意
味着我们可以使用 defaultMatcher 类型的值或者指向这个类型值的指针来调用 Search 方
法。无论我们是使用接收者类型的值来调用这个方，还是使用接收者类型值的指针来调用这个
方法，编译器都会**正确地引用或者解引用**对应的值。

---

```go
// 方法声明为使用指向 defaultMatcher 类型值的指针作为接收者
func (m *defaultMatcher) Search(feed *Feed, searchTerm string)
// 通过 interface 类型的值来调用方法
var dm defaultMatcher
var matcher Matcher = dm // 将值赋值给接口类型
matcher.Search(feed, "test") // 使用值来调用接口方法
> go build
cannot use dm (type defaultMatcher) as type Matcher in assignment
// 方法声明为使用 defaultMatcher 类型的值作为接收者
func (m defaultMatcher) Search(feed *Feed, searchTerm string)
// 通过 interface 类型的值来调用方法
var dm defaultMatcher
var matcher Matcher = &dm // 将指针赋值给接口类型
matcher.Search(feed, "test") // 使用指针来调用接口方法
> go build
Build Successful
```