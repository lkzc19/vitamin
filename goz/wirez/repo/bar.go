package repo

import (
	"fmt"
)

type IBarRepo interface {
	Hello()
}

type BarRepo string

func provideBarRepo() *BarRepo {
	b := new(BarRepo)
	*b = "Hello, World!"
	return b
}

func (bar *BarRepo) Hello() {
	fmt.Println("=== bar a ===")
	fmt.Println(bar)
	fmt.Println("=== bar z ===")
}
