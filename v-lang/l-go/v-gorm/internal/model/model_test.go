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
	um := time.UnixMilli(1720688720863)
	fmt.Println(um)
	f := um.Year() >= 1970 && um.Year() <= 9999
	fmt.Println(f)

	um = time.UnixMilli(1)
	fmt.Println(um)
	f = um.Year() >= 1970 && um.Year() <= 9999
	fmt.Println(f)
}

func Test3(t *testing.T) {
	// 获取当前时间
	now := time.Now()

	// 生成近 30 天的日期
	for i := 0; i < 30; i++ {
		date := now.AddDate(0, 0, -i)
		fmt.Println(date.Format("2006-01-02"))
	}
}
