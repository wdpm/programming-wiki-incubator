package main

import "testing"

func TestLongRunning(t *testing.T) {
	if testing.Short() {
		t.Skip("skipping long-running test")
	}
	// ...
}

// we’ve seen three ways to categorize tests:
//  Using build tags at the test file level
//  Using environment variables to mark a specific test
//  Based on the test pace using short mode
