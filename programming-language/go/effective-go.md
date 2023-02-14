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