package timer

import "testing"

func BenchmarkFoo1(b *testing.B) {
	expensiveSetup()
	// exclude expensiveSetup() time cost
	b.ResetTimer()
	for i := 0; i < b.N; i++ {
		functionUnderTest()
	}
}

func BenchmarkFoo2(b *testing.B) {
	for i := 0; i < b.N; i++ {
		// pause timer
		b.StopTimer()
		expensiveSetup()
		// after expensive Setup, re-start timer
		b.StartTimer()
		functionUnderTest()
	}
}

func functionUnderTest() {
}

func expensiveSetup() {
}
