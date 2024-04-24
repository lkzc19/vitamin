package repo

import (
	"fmt"
)

type IBarRepo interface {
	Repo[string]
	Bar()
}

type BarRepo string

func ProvideBarRepo() *BarRepo {
	r := new(BarRepo)
	*r = "Hello, Bar!"
	return r
}

func (r *BarRepo) Bar() {
	fmt.Println("=== BarRepo.Bar ===")
	fmt.Println(*r)
}

func (r *BarRepo) Add(str *string) (*string, error) {
	newStr := *str + *str
	return &newStr, nil
}
