package model

import (
	"fmt"
	"testing"
	"time"
)

func Test1(t *testing.T) {
	Println("nahida", "hutao")
}

func Println(str ...string) {
	for i, v := range str {
		fmt.Println(i, v)
	}
}

func Test2(t *testing.T) {

	fmt.Println(time.UnixMilli(1720688720863))
}
