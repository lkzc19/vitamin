package main

import (
	"fmt"
	"github.com/shopspring/decimal"
	"testing"
)

func TestDecimal(t *testing.T) {
	numStr := "1"
	d, err := decimal.NewFromString(numStr)
	if err != nil {
		t.Failed()
	}
	fmt.Println(d.String())
}
