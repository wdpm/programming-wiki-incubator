package main

import (
	"golang.org/x/tour/wc"
	"strings"
)

func WordCount(s string) map[string]int {
	m := make(map[string]int) // map对象
	c := strings.Fields(s)    // []string
	for _, v := range c {
		m[v] += 1 // 如果v没有在map中，m[v]的值为初始值 0
	}
	return m
}

// 字符串 s 中每个“单词”的个数
func main() {
	wc.Test(WordCount)
}

//PASS
//f("I am learning Go!") =
//map[string]int{"Go!":1, "I":1, "am":1, "learning":1}

//PASS
//f("The quick brown fox jumped over the lazy dog.") =
//map[string]int{"The":1, "brown":1, "dog.":1, "fox":1, "jumped":1, "lazy":1, "o
//ver":1, "quick":1, "the":1}

//PASS
//f("I ate a donut. Then I ate another donut.") =
//map[string]int{"I":2, "Then":1, "a":1, "another":1, "ate":2, "donut.":2}

//PASS
//f("A man a plan a canal panama.") =
//map[string]int{"A":1, "a":2, "canal":1, "man":1, "panama.":1, "plan":1}
