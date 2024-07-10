package model

import (
	"fmt"
	"testing"
)

func Test1(t *testing.T) {
	Println("nahida", "hutao")
}

func Println(str ...string) {
	for i, v := range str {
		fmt.Println(i, v)
	}
}
