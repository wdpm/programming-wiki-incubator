# effective go

*effective go* 一书的摘要笔记。

## chapter 2 testing

|Method|Purpose|
|---|---|
|t.Log(args ...any)|Logs a message to the test output|
|t.Logf(format string, args ...any)| Logs a formatted log message to the test output|
|t.Fail() |Marks the test as a failed test and keeps running the test function|
|t.FailNow()| Marks the test as a failed test and stops running the test function|
|t.Error(args ...any)|It is equivalent to calling Log and Fail methods|
|t.Errorf(format string, args ...any) |It is equivalent to calling Logf and Fail methods|
|t.Fatal(args ...any)|It is equivalent to calling Log and FailNow methods|
|t.Fatalf(format string, args ...any)| It is equivalent to calling Logf and FailNow methods|

`if variable := value; condition` is called a short-if declaration. You
can use it to declare the variables in the scope of the if statement. Declaring
variables in a shorter scope is a good practice that helps avoid polluting the
scope namespace with unnecessary variables

## chapter 3 Fighting with complexity

the shuffle option only affects the top-level tests.

```bash
go test -v -shuffle=on
go test -v -shuffle=[seed_number]
```

不能依赖 MAP 的顺序，参考：https://go.dev/doc/go1#iteration 。

> In Go 1, the order in which elements are visited when iterating over a map using a for range statement is defined to
> be unpredictable, even if the same loop is run multiple times with the same map. Code should not assume that the
> elements are visited in any particular order.

---

使用 subtest 来部分运行测试，层级是嵌套的，互相隔离。t.Run(test_name,subtest_func_definition)

---

failfast option

```bash
$ go test –failfast
--- FAIL: TestURLPort
--- FAIL: TestURLPort/with_port (0.00s)
url_test.go:2: for host "foo.com:80"; got ""; want"80"
```

---

run selected tests

```bash
go test -v -run=TestParse
go test -v -run=TestParse$
$ go test -run=TestURLPort/^with_port
```

例子：

```bash
$ go test -v -run=URL/without
=== RUN TestURLString
=== RUN TestURLPort
=== RUN TestURLPort/without_port
=== RUN TestURLPort/ip_without_port
```

```
func TestURLPort(t *testing.T) {
  testPort := func (in, wantPort string) func(*testing.T) { #A
    return func (t *testing.T) { #A
        t.Helper()
        u := &URL{Host: in}
        if got := u.Port(); got != wantPort {
          t.Errorf("for host %q; got %q; want %q", in, got, wantPort)
        }
    } #A
  }
  t.Run("with port", testPort("foo.com:80", "80")) #B
  t.Run("with empty port", testPort("foo.com:", "")) #B
  t.Run("without port", testPort("foo.com", "")) #B
  t.Run("ip with port", testPort("1.2.3.4:90", "90")) #B
  t.Run("ip without port", testPort("1.2.3.4", "")) #B
}
```

```bash
$ go test -v -run=TestURLPort//foo.com
$ go test -v -run=TestURLPort/*/foo.com
```

上面两行等价。

## chapter 4 Tidying up

This chapter covers:

- Writing testable examples
- Producing executable documentation
- Measuring test coverage and benchmarking
- Refactoring the URL parser
- Differences between external and internal tests

### Testable examples

1. enable `GO111MODULE`, and set `GOPROXY`:

```bash
set GO111MODULE=on
set GOPROXY=https://mirrors.aliyun.com/goproxy/,direct
```

2. install godoc

```bash
go install golang.org/x/tools/cmd/godoc@latest
```

3. run godoc

```bash
godoc -play -http ":6060"
http://localhost:6060/pkg/github.com/inancgumus/effective-go/ch04
```

### Test coverage

```bash
go test -coverprofile cover.out
```

Here is the result:

```bash
PASS
coverage: 93.9% of statements
ok      github.com/inancgumus/effective-go/ch04/url     0.666s
```

观察 cover.out 文件的输出格式：

```bash
mode: set
github.com/inancgumus/effective-go/ch04/url/url.go:18.41,20.9 2 1
github.com/inancgumus/effective-go/ch04/url/url.go:23.2,24.38 2 1
github.com/inancgumus/effective-go/ch04/url/url.go:20.9,22.3 1 1
github.com/inancgumus/effective-go/ch04/url/url.go:27.64,33.2 1 1
...
```

这种格式不好阅读，下面使用更加友好的工具。

基本命令如下：

```bash
Given a coverage profile produced by 'go test':
        go test -coverprofile=c.out

Open a web browser displaying annotated source code:
        go tool cover -html=c.out

Write out an HTML file instead of launching a web browser:
        go tool cover -html=c.out -o coverage.html

Display coverage percentages to stdout for each function:
        go tool cover -func=c.out
```

我们先输出 html 看下效果：

```bash
...\effective-go\ch04\url>go tool cover -html=cover.out
```

- The green lines are the areas in the code where tests cover.
- The red lines are where tests don't cover.
- The gray lines are untracked by the coverage tool

---
也可以在控制台输出 func 覆盖的信息统计：

```bash
...\effective-go>go tool cover -html=cover.out
github.com/inancgumus/effective-go/ch04/url/url.go:27:  parseScheme     100.0%
github.com/inancgumus/effective-go/ch04/url/url.go:35:  parseHostPath   75.0%
github.com/inancgumus/effective-go/ch04/url/url.go:44:  Hostname        100.0%
github.com/inancgumus/effective-go/ch04/url/url.go:55:  Port            100.0%
github.com/inancgumus/effective-go/ch04/url/url.go:63:  split           100.0%
github.com/inancgumus/effective-go/ch04/url/url.go:72:  String          100.0%
github.com/inancgumus/effective-go/ch04/url/url.go:106: testString      0.0%
total:                                                  (statements)    93.9%
```

- 个别 private 函数可能没有覆盖，需要补充测试。

---
这个命令只关注覆盖率。

```bash
go test -cover 
```

### 4.3 Benchmarks

```bash
...\effective-go\ch04\url>go test -bench . -v
=== RUN   TestParse
--- PASS: TestParse (0.00s)
=== RUN   TestParseNoPath
--- PASS: TestParseNoPath (0.00s)
...
goos: windows
goarch: amd64
pkg: github.com/inancgumus/effective-go/ch04/url
cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
BenchmarkURLString
BenchmarkURLString-8     9197532               142.5 ns/op
PASS
ok      github.com/inancgumus/effective-go/ch04/url     2.464s
```

- BenchmarkURLString-8 表示 8 核 CPU，
- 9197532 表示操作数 operations，
- 142.5 ns/op 表示每个 op 消耗的时间。

---

仅运行 bench 测试的函数：

```bash
go test -run=^$ -bench . -v
```

---

memory and loop time count

> BenchmarkURLString()

```bash
//BenchmarkURLString
//    url_test.go:114: Loop time 1
//    url_test.go:114: Loop time 100
//    url_test.go:114: Loop time 10000
//    url_test.go:114: Loop time 1000000
//    url_test.go:114: Loop time 6649539
//    url_test.go:114: Loop time 9788378
//BenchmarkURLString-8     9788378               131.3 ns/op            56 B/op
//               3 allocs/op
```

- 56 B/op 表示一次 OP 一共分配 56Bytes。
- 3 allocs/op 表示一次 OP 含有三次内存申请。

---

Comparing benchmarks。

1. Save the benchmark result of the String method.
2. Find out how you can optimize it.
3. Remeasure it and compare it with the previous result.

```bash
go test -bench . -count 10 > old.txt
```

会在当前目录下生成一个 old.txt 文件，记录了 10 次 benchmark 的统计信息。

```bash
go test -bench . -count 10 > new.txt
```

梅开二度，记录新代码的性能测试信息到 new.txt。

下面就是对比结果，评断是否真的优化了，还是开倒车了。人工肉眼对比显然是力大砖飞的结果。可以借助命令 benchstat 来进行对比。

这是一个第三方库，因此需要安装。

```bash
go install golang.org/x/perf/cmd/benchstat@latest
```

进行对比：

```bash
...\effective-go\ch04\url>benchstat old.txt new.txt 
goos: windows
goarch: amd64
pkg: github.com/inancgumus/effective-go/ch04/url
cpu: Intel(R) Core(TM) i7-8550U CPU @ 1.80GHz
            │   old.txt   │               new.txt                │
            │   sec/op    │    sec/op     vs base                │
URLString-8   204.9n ± 8%   135.1n ± 14%  -34.08% (p=0.000 n=10)

            │  old.txt   │              new.txt               │
            │    B/op    │    B/op     vs base                │
URLString-8   64.00 ± 0%   56.00 ± 0%  -12.50% (p=0.000 n=10)

            │  old.txt   │              new.txt               │
            │ allocs/op  │ allocs/op   vs base                │
URLString-8   4.000 ± 0%   3.000 ± 0%  -25.00% (p=0.000 n=10)
```

Measuring the performance of the String method with different URL values
can give you more accurate results. You can use sub-benchmarks to do that.
Similar to subtests, you can run multiple sub-benchmarks under a single
benchmark function

### Refactoring

指责分离，小函数。

- python 变量修饰: public、_protected（只是一种约定，依旧可以访问）、__private（只是一种伪装，依旧可以通过 `_ 类名 __private` 访问）
- java 变量修饰：public、default:packaged（子类或者同包）、protected（子类可以访问）、private（自己可以访问）
- go 变量修饰：public（大写字母开头），private（小写字母开头）。

The %q verb wraps a string in double-quotes. The %#v verb formats a value in
the Go syntax. You can find all the other verbs at the link:
https://pkg.go.dev/fmt

一个比较库。
> go get -u github.com/google/go-cmp/cmp

---
4.5 External tests

`export_test.go`

```go 
package url
var ParseScheme = parseScheme #A
```

- You exported the parseScheme function from the url package.
- Since the ParseScheme variable is in a test file, the test tool will compile it alongside the url package.
- So you can access the variable in our test files as if it was being exported from the url package!

parse_scheme_test.go 使用例子。

- External tests reside in a package with a _test suffix and cannot access
  the unexported identifiers of a package. But they can verify the internals
  of a package using a trick to export an unexported identifier via an
  exported one. On the other hand, internal tests reside in the same
  package as the code they test, and they can access both exported and
  unexported identifiers from the package.
- Internal tests are white-box tests and verify code from the same package.
- External tests are black-box tests and verify code from an external test
  package.
- One benefit of external tests is that they verify the visible behavior of
  code, and these tests can only break if the API of the code changes
  (unless there is a bug).

---

- Testable examples allow you to create documentation that never goes out of date.
- Test coverage helps you find untested code areas, but it doesn't guarantee 100% bug-free code.
- You can measure code performance using benchmarks.
- Refactoring helps you create maintainable code, and tests help you to refactor your code without fear.
- External tests allow you to write black-box tests and test the public area
  of a package. Internal tests allow you to write white-box tests and test every aspect of a package

## cli tool

### init new go project

```bash
$ go mod init github.com/your_username/project_name
```

查看本地 go 的安装系统和架构

```bash
go env GOOS GOARCH
go tool dist list -json
GOOS=windows GOARCH=amd64 go build
go build -o bin/hit.exe
GOOS=windows GOARCH=amd64 go build -o bin/hit.exe
```

准备一个 MakeFile，内容如下：

```makefile
compile:
	# compile it for linux
	GOOS=linux GOARCH=amd64 go build -o ./bin/hit_linux_amd64 ./cmd/hit
	# compile it for macOS
	GOOS=darwin GOARCH=amd64 go build -o ./bin/hit_darwin_amd64 ./cmd/hit
	# compile it for Apple M1
	GOOS=darwin GOARCH=arm64 go build -o ./bin/hit_darwin_arm64 ./cmd/hit
	# compile it for Windows
	GOOS=windows GOARCH=amd64 go build -o ./bin/hit_win_amd64.exe ./cmd/hit
```

需要 make 工具和 go 同时正确安装。要么在宿主环境安装，要么在 WSL2 环境安装（以 Windows10 为例）.

这是在 WSL2（go 1.18 + GNU Make 4.3 Built for x86_64-pc-linux-gnu ）下的编译结果：
> Windows OS 下 MinGW 这类工具年久失修，不再建议使用。

```bash
root@DESKTOP-QLDBOG2:/mnt/d/Code/MyGithubProjects/effective-go/ch05/bin# tree
.
├── hit_darwin_amd64
├── hit_darwin_arm64
├── hit_linux_amd64
└── hit_win_amd64.exe
```

strconv.ParseInt vs. strconv.Atoi

Go 一个 package 中只能有一个 main func，除非你将 scope 改成 file。

---

Go build tags 构建忽略设置。参考文件：hit_test.go

https://pkg.go.dev/cmd/go#hdr-Build_constraints

### Exercises

> TODO

Make sure to add relevant fields (to the flags struct) and tests for the
following exercises.

1. Add a new timeout flag using the DurationVar method:

```bash
$ go run . -t=5s http://foo
Making ... (Timeout=5s).
```

2. Add a new flag with a new dynamic value type that accepts only the
   following values: GET, POST, and PUT:

```bash 
$ go run . -m=GET http://foo
Making 100 GET requests to...
$ go run . -m=FETCH http://foo
invalid value "FETCH" for flag -m: incorrect method: "FETCH"
```

Add a new flag with a new dynamic value type that puts HTTP headers into a
slice:

```bash
$ go run . -H='Accept: text/json' -H='User-Agent: hit' http://foo
Headers: "Accept: text/json", "User-Agent: hit"
Making ...
```

Tip: The user passes the same flag twice, and the flag package calls the Set
method of the dynamic value twice! You can use this fact and append the
values to a slice in the dynamic type.

## 6 Concurrent API Design

```bash
go run . -n 1000 -c 10 -t 1 http://localhost:9090
```

1000 个请求 ，并发度为 10（这里指代 goroutines 有 10 个），-t 表示 rps 属性，个人觉得这里还不如使用 -rps。

```
s.Var(toNumber(&f.rps), "t", "Throttle requests per second")
```

-t=1，表示 rps=1，也就是期望 1 秒只有一个请求。但是，这里并发度为 10，那么该如何理解呢？

> You told the hit tool to limit the requests per second to one. Since there were
> ten goroutines, the client adjusted the throttling to ten.

在本地启动一个简易的测试服务器。

```bash
py -m http.server 9090
```

参照 1

```bash
$ go run . -n 1000 -c 10  http://localhost:9090

Making 1000 requests to http://localhost:9090 with a concurrency level of 10.

Summary:
        RPS        : 443.0
        Duration   : 2.257449s
```

参照 2

```bash
$ go run . -n 1000 -c 1  http://localhost:9090

Making 1000 requests to http://localhost:9090 with a concurrency level of 1.

Summary:
        RPS        : 347.0
        Duration   : 2.881779s
```

试验 1

```bash
$ go run . -n 1000 -c 10 -t 1  http://localhost:9090

Making 1000 requests to http://localhost:9090 with a concurrency level of 10.
(RPS: 1)

Summary:
        RPS        : 10.0
        Duration   : 1m40.006274s
```

`-c 10 -t 1` => (RPS: 1)，这里的 RPS 只是显示问题，实际并发度是 1*10=10 个请求 /s。
因为有 1000 个请求，因此粗略计算为 1000/10=100s=1m40s，和结果基本吻合。

可以看到，限流的作用非常明显。`-c 10 -t 1` 的限制能力远远强于 `-c 1`。

- `-t 1` 直接从平均时间的并发数指标上锁死了程序的并发能力。
- `-c 1` 只是限制了 goroutine 的数量，即使只有一个 goroutine，程序依旧不慢，因为不需要间隔性等待。

### Graceful cancellation

Imagine you want to send millions of requests and, for some reason, want to
cancel the ongoing work. Or, you might want to stop sending requests after a
specific time (timeout).

=> use context.Context

---

Canceling in-flight requests

When the context is canceled, you stopped producing more request values,
but what about the ones already in progress?

Fortunately, the http package allows you to stop an in-flight request. You can
do it by cloning a request with a cancelable context.

---

Go 的错误处理： https://go.dev/blog/go1.13-errors

---

中途取消：Ctrl + C => SIGNAL

=> you can use the signal package's `NotifyContext` function to
catch the signal.

### Sending HTTP requests

Tweaking the connection pool option on transport layer.

### 6.5 Testing

summary:

- Achieving an effective architecture is mostly about reducing complexity
  by dividing a task into composable parts where each will be responsible
  for doing a smaller set of tasks.
- API is what you export from a package. Hide complexity behind a
  simple and synchronous API and let other people decide when to use
  your API concurrently. Concurrency is an implementation detail.
- Concurrency is structuring a program as independently executing
  components. A concurrent pipeline is an extensible and efficient design
  pattern consisting of concurrent stages. You can easily add and remove
  stages and compose different pipelines without changing stage code.
- Spawning goroutines is easy, but shutting them down is not. It's because
  the Go language does not offer a way to stop a goroutine, at least not
  directly. Fortunately, the context package provides a straightforward
  way to stop goroutines.
- The http package allows you to send HTTP requests, and the httptest
  package can launch a test server to test code that sends HTTP requests.
- Rob Pike's option functions pattern lets you provide a customizable API
  without complicating the API surface area