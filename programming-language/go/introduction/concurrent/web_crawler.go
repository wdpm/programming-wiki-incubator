package main

import (
	"fmt"
	"sync"
)

type Fetcher interface {
	// Fetch 返回 URL 的 body 内容，并且将在这个页面上找到的 URL 放到一个 slice 中。
	Fetch(url string) (body string, urls []string, err error)
}

type SafeMap struct {
	v map[string]bool
	sync.Mutex
}

var safeMap = SafeMap{
	v: make(map[string]bool),
}

// Crawl 使用 fetcher 从某个 URL 开始递归的爬取页面，直到达到最大深度。
func Crawl(url string, depth int, fetcher Fetcher, waitGroup *sync.WaitGroup) {
	// 利用go routine 并行抓取 URL。
	// 利用map避免重复抓取页面。

	defer waitGroup.Done()

	if depth <= 0 {
		return
	}

	safeMap.Lock()
	if isFetch := safeMap.v[url]; isFetch {
		fmt.Println(url, " is fetched")
		safeMap.Unlock()
		return
	} else {
		safeMap.v[url] = true
		safeMap.Unlock()
	}

	body, urls, err := fetcher.Fetch(url)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Printf("found: %s %q\n", url, body)

	wg := sync.WaitGroup{}
	for _, u := range urls {
		wg.Add(1)
		go Crawl(u, depth-1, fetcher, &wg)
	}
	wg.Wait()

	return
}

func main() {
	// 群组等待: 当添加的任务add() 没有完成时done()， 会一直等待wait()
	wg := sync.WaitGroup{}
	wg.Add(1)
	go Crawl("https://golang.org/", 4, fetcher, &wg)
	wg.Wait()
}

// fakeFetcher 是返回若干结果的 Fetcher。
type fakeFetcher map[string]*fakeResult

type fakeResult struct {
	body string
	urls []string
}

func (f fakeFetcher) Fetch(url string) (string, []string, error) {
	if res, ok := f[url]; ok {
		return res.body, res.urls, nil
	}
	return "", nil, fmt.Errorf("not found: %s", url)
}

// fetcher 是填充后的 fakeFetcher。
var fetcher = fakeFetcher{
	"https://golang.org/": &fakeResult{
		"The Go Programming Language",
		[]string{
			"https://golang.org/pkg/",
			"https://golang.org/cmd/",
		},
	},
	"https://golang.org/pkg/": &fakeResult{
		"Packages",
		[]string{
			"https://golang.org/",
			"https://golang.org/cmd/",
			"https://golang.org/pkg/fmt/",
			"https://golang.org/pkg/os/",
		},
	},
	"https://golang.org/pkg/fmt/": &fakeResult{
		"Package fmt",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
	"https://golang.org/pkg/os/": &fakeResult{
		"Package os",
		[]string{
			"https://golang.org/",
			"https://golang.org/pkg/",
		},
	},
}
