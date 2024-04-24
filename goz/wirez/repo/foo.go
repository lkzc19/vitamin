package repo

import (
	"fmt"
	"wirez/common"
)

type IFooRepo interface {
	Foo()
}

type FooRepo struct {
	DB common.PgsqlStr
}

func ProvideFooRepo(db common.PgsqlStr) *FooRepo {
	return &FooRepo{DB: db}
}

func (foo FooRepo) Foo() {
	fmt.Println("=== FooBar.Foo ===")
	fmt.Println(foo.DB)
}
