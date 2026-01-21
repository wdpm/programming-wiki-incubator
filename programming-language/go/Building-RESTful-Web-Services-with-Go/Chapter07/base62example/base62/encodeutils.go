package base62

import (
	"math"
	"strings"
)

// 10 + 26 + 26
const base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
const b = 62

// ToBase62 Function encodes the given database ID to a base62 string
func ToBase62(num int) string {
	r := num % b
	res := string(base[r])
	div := num / b
	q := int(math.Floor(float64(div)))

	for q != 0 {
		r = q % b
		temp := q / b
		q = int(math.Floor(float64(temp)))
		res = string(base[r]) + res
	}

	return res
}

// ToBase10 Function decodes a given base62 string to datbase ID
func ToBase10(str string) int {
	res := 0
	for _, r := range str {
		res = (b * res) + strings.Index(base, string(r))
	}
	return res
}
