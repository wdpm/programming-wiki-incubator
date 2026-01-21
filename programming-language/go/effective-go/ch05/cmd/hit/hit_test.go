//go:build cli

// you don't want to run the TestRun test until you pass a tag called cli
// usage: go test -tags=cli

package main

import (
	"bytes"
	"flag"
	"runtime"
	"strconv"
	"strings"
	"testing"
)

func TestRun(t *testing.T) {
	// go test . --short -v
	if testing.Short() {
		t.SkipNow()
	}

	// top-level, relative to TestRun func
	t.Parallel()

	happy := map[string]struct{ in, out string }{
		"url": {
			"http://foo",
			"100 requests to http://foo with a concurrency level of " +
				strconv.Itoa(runtime.NumCPU()),
		},
		"n_c": {
			"-n=20 -c=5 http://foo",
			"20 requests to http://foo with a concurrency level of 5",
		},
	}
	sad := map[string]string{
		"url/missing": "",
		"url/err":     "://foo",
		"url/host":    "http://",
		"url/scheme":  "ftp://",
		"c/err":       "-c=x http://foo",
		"n/err":       "-n=x http://foo",
		"c/neg":       "-c=-1 http://foo",
		"n/neg":       "-n=-1 http://foo",
		"c/zero":      "-c=0 http://foo",
		"n/zero":      "-n=0 http://foo",
		"c/greater":   "-n=1 -c=2 http://foo",
	}
	for name, tt := range happy {
		//ensures that each subtest gets a fresh copy of a test case
		tt := tt
		t.Run(name, func(t *testing.T) {
			// subtest-level, relative to this happy subtest
			t.Parallel()

			e := &testEnv{args: tt.in}
			if err := e.run(); err != nil {
				t.Fatalf("got %q;\nwant nil err", err)
			}
			// 关键点：happy 测试使用的是stdout的输出，进行对比
			if out := e.stdout.String(); !strings.Contains(out, tt.out) {
				t.Errorf("got:\n%s\nwant %q", out, tt.out)
			}
		})
	}
	for name, in := range sad {
		in := in
		t.Run(name, func(t *testing.T) {
			t.Parallel()

			e := &testEnv{args: in}
			if e.run() == nil {
				t.Fatal("got nil; want err")
			}
			// 关键点：sad 测试使用的是stderr的输出，进行对比
			if e.stderr.Len() == 0 {
				t.Fatal("stderr = 0 bytes; want >0")
			}
		})
	}
}

type testEnv struct {
	args           string
	stdout, stderr bytes.Buffer
}

func (e *testEnv) run() error {
	s := flag.NewFlagSet("hit", flag.ContinueOnError)
	s.SetOutput(&e.stderr)
	return run(s, strings.Fields(e.args), &e.stdout)
}
