package service

import (
	"fmt"
	"wirez/repo"
)

type IHelloService interface {
	HelloFoo()
	HelloBar()
}

type HelloService struct {
	FooRepo repo.IFooRepo
	BarRepo repo.IBarRepo
}

func (s *HelloService) HelloFoo() {
	fmt.Println("=== HelloService.HelloFoo ===")
	s.FooRepo.Foo()
}

func (s *HelloService) HelloBar() {
	fmt.Println("=== HelloService.HelloBar ===")
	s.BarRepo.Bar()

	str := "bar"
	add, err := s.BarRepo.Add(&str)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(*add)
	}
}
