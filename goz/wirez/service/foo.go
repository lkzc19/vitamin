package service

import (
	"wirez/repo"
)

type IFooService interface {
	Hello()
}

type FooService struct {
	FooRepo repo.IFooRepo
}

func (foo *FooService) Hello() {
	foo.FooRepo.Hello()
}
