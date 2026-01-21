package main

func main() {
	w := newWatcher()
	// 定义清晰的关闭method，主动清理持有的资源
	defer w.close()

	// Run the application
}

func newWatcher() watcher {
	w := watcher{}
	go w.watch()
	return w
}

type watcher struct { /* Some resources */
}

func (w watcher) watch() {}

func (w watcher) close() {
	// Close the resources
}
