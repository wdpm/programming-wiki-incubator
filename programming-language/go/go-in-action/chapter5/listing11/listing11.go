// Sample program to show how to declare methods and how the Go
// compiler supports them.
package main

import (
	"fmt"
)

// user defines a user in the program.
type user struct {
	name  string
	email string
}

// notify implements a method with a value receiver.
// notify 方法的接收者被声明为 user 类型的值。如果使用值接收者声明方法，调用时会使用这个值的一个副本来执行。
func (u user) notify() {
	fmt.Printf("Sending User Email To %s<%s>\n",
		u.name,
		u.email)
}

// changeEmail implements a method with a pointer receiver.
func (u *user) changeEmail(email string) {
	u.email = email
}

// main is the entry point for the application.
func main() {
	// Values of type user can be used to call methods
	// declared with a value receiver.
	bill := user{"Bill", "bill@email.com"}
	bill.notify()

	// Pointers of type user can also be used to call methods
	// declared with a value receiver.
	lisa := &user{"Lisa", "lisa@email.com"}
	// (*lisa).notify() => dereference and copy lisa value
	lisa.notify()

	// Values of type user can be used to call methods
	// declared with a pointer receiver.
	// (&bill).changeEmail ("bill@newdomain.com")
	bill.changeEmail("bill@newdomain.com")
	bill.notify()

	// Pointers of type user can be used to call methods
	// declared with a pointer receiver.
	lisa.changeEmail("lisa@newdomain.com")
	// (*lisa).notify()
	lisa.notify()

	// 总结一下，值接收者使用值的副本来调用方法，而指针接受者使用实际值来调用方法
}
