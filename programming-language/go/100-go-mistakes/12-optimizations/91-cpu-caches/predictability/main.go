package main

type node struct {
	value int64
	next  *node
}

// for the linked list, we face a non-unit stride
func linkedList(n *node) int64 {
	var total int64
	for n != nil {
		total += n.value
		n = n.next
	}
	return total
}

// For sum2, we face a constant CPU stride
func sum2(s []int64) int64 {
	var total int64
	for i := 0; i < len(s); i += 2 {
		total += s[i]
	}
	return total
}
