package main

import (
	"log"
	"os"
)

func listing1(filename string) error {
	f, err := os.OpenFile(filename, os.O_APPEND|os.O_WRONLY, os.ModeAppend)
	if err != nil {
		return err
	}

	defer func() {
		if err := f.Close(); err != nil {
			log.Printf("failed to close file: %v\n", err)
		}
	}()

	return nil
}

func writeToFile1(filename string, content []byte) (err error) {
	f, err := os.OpenFile(filename, os.O_APPEND|os.O_WRONLY, os.ModeAppend)
	if err != nil {
		return err
	}

	defer func() {
		closeErr := f.Close()
		// Returns the close error
		// if the write succeeds
		if err == nil {
			err = closeErr
		}
	}()

	_, err = f.Write(content)
	return
}

func writeToFile2(filename string, content []byte) (err error) {
	f, err := os.OpenFile(filename, os.O_APPEND|os.O_WRONLY, os.ModeAppend)
	if err != nil {
		return err
	}

	defer func() {
		_ = f.Close()
	}()

	_, err = f.Write(content)
	if err != nil {
		return err
	}

	// force a flush to disk
	// This example is a synchronous write function. It ensures that the content is written to
	// disk before returning. But its downside is an impact on performance
	return f.Sync()
}
