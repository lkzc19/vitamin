package repo

import (
	"fmt"
	"wirez/common"
)

type IBarRepo interface {
	Repo[string]
	Bar()
}

type BarRepo struct {
	db common.PgsqlStr
}

func ProvideBarRepo(db common.PgsqlStr) *BarRepo {
	return &BarRepo{db: db}
}

func (r *BarRepo) Bar() {
	fmt.Println("=== BarRepo.Bar ===")
	fmt.Println(r.db)
}

func (r *BarRepo) Add(str *string) (*string, error) {
	newStr := *str + *str
	return &newStr, nil
}
