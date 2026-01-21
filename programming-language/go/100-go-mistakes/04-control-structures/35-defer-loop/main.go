package main

import "os"

func readFiles1(ch <-chan string) error {
	for path := range ch {
		file, err := os.Open(path)
		if err != nil {
			return err
		}

		// In this case, the defer
		// calls are executed not during each loop iteration but when the readFiles1 function
		// returns
		defer file.Close()

		// Do something with file
	}
	return nil
}

// recommended practice
func readFiles2(ch <-chan string) error {
	for path := range ch {
		if err := readFile(path); err != nil {
			return err
		}
	}
	return nil
}

func readFile(path string) error {
	file, err := os.Open(path)
	if err != nil {
		return err
	}

	defer file.Close()

	// Do something with file
	return nil
}

func readFiles3(ch <-chan string) error {
	for path := range ch {
		// For performance: get rid of defer and
		// handle the defer call manually before looping
		err := func() error {
			file, err := os.Open(path)
			if err != nil {
				return err
			}

			defer file.Close()

			// Do something with file
			return nil
		}()

		if err != nil {
			return err
		}
	}
	return nil
}

// When using defer, we must remember that it schedules a function call when the
// surrounding function returns.
