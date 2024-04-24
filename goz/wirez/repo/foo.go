package repo

import (
	"fmt"
	"wirez/common"
)

type IFooRepo interface {
	Foo()
}

type FooRepo struct {
	db common.PgsqlStr
}

func ProvideFooRepo(db common.PgsqlStr) *FooRepo {
	return &FooRepo{db: db}
}

func (foo FooRepo) Foo() {
	fmt.Println("=== FooBar.Foo ===")
	fmt.Println(foo.db)
}
