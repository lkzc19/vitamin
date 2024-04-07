package repo

import (
	"fmt"
	"wirez/common"
)

type IFooRepo interface {
	Hello()
}

type FooRepo struct {
	DB common.PgsqlStr
}

func ProvideFooRepo(db common.PgsqlStr) *FooRepo {
	return &FooRepo{DB: db}
}

func (foo FooRepo) Hello() {
	fmt.Println(foo.DB)
}
