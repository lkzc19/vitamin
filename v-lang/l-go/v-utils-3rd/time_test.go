package main

import (
	"fmt"
	"testing"
	"time"
)

func TestZero(t *testing.T) {
	t1 := time.Time{}
	fmt.Println(t1)

	fmt.Println(t1.IsZero())
}
