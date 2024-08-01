package timex

import (
	"fmt"
	"testing"
	"time"
)

func Test1(t *testing.T) {
	date := time.Date(2024, 7, 31, 0, 0, 0, 0, time.Local)
	fmt.Println(fmt.Sprintf("%d-%d-%d", date.Year(), date.Month(), date.Day()))
	date1 := date.AddDate(-1, 0, 0)
	fmt.Println(fmt.Sprintf("%d-%d-%d", date1.Year(), date1.Month(), date1.Day()))
	date2 := date.AddDate(-1, -1, 0)
	fmt.Println(fmt.Sprintf("%d-%d-%d", date2.Year(), date2.Month(), date2.Day()))
}

func TestIsLastMonth(t *testing.T) {
	date := time.Date(2023, 6, 30, 0, 0, 0, 0, time.Local)
	fmt.Println(isLastMonth(date))
}
