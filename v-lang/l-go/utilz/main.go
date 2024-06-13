package main

import (
	"fmt"
	"github.com/shopspring/decimal"
)

func main() {
	numStr := "1"
	d, err := decimal.NewFromString(numStr)
	if err != nil {
		panic(err)
	}
	fmt.Println(d.String())
}
