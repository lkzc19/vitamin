package timex

import "time"

func isLastMonth(t time.Time) bool {
	now := time.Now()
	y := now.Year()
	m := now.Month() - 1
	if m == 0 {
		y -= 1
	}
	return y == t.Year() && m%12 == t.Month()
}
