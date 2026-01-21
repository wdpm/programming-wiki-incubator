package main

const n = 1_000_000

func add(s [2]int64) [2]int64 {
	for i := 0; i < n; i++ {
		// read&write s[0]
		s[0]++
		// read s[0]
		if s[0]%2 == 0 {
			// read&write s[1]
			s[1]++
		}
	}
	return s
}

func add2(s [2]int64) [2]int64 {
	for i := 0; i < n; i++ {

		// s[0]++ =>
		v := s[0]
		s[0] = v + 1

		// 增加后为偶数 <=> 不增加之前为奇数，完全等价
		if v%2 != 0 {
			s[1]++
		}
	}
	return s
}
