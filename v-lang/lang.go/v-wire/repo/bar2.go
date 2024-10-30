package repo

import (
	"fmt"
	"wirez/common"
)

type IBar2Repo interface {
	Repo[string]
	Bar()
}

type Bar2Repo struct {
	db common.PgsqlStr
}

func ProvideBar2Repo(db common.PgsqlStr) *Bar2Repo {
	return &Bar2Repo{db: db}
}

func (r *Bar2Repo) Bar() {
	fmt.Println("=== BarRepo.Bar ===")
	fmt.Println(r.db)
}

func (r *Bar2Repo) Add(str *string) (*string, error) {
	newStr := *str + *str
	return &newStr, nil
}
