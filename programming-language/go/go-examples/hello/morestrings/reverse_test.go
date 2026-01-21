package morestrings

import "testing"

func TestReverseRunes(t *testing.T) {
	type args struct {
		s string
	}
	tests := []struct {
		name string
		args args
		want string
	}{
		// Add test cases.
		{"case 1", args{s: "123"}, "321"},
		{"case 2", args{s: "dlrow ,olleH"}, "Hello, world"},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := ReverseRunes(tt.args.s); got != tt.want {
				t.Errorf("ReverseRunes() = %v, want %v", got, tt.want)
			}
		})
	}
}
