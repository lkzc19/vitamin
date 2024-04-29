package main

import (
	"fmt"
	"time"
)

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}

func t(f func()) {
	start := time.Now()
	f()
	end := time.Now()
	elapsed := end.Sub(start)
	fmt.Printf("程序执行时间: %s\n", elapsed)
}
