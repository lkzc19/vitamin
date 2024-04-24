package service

import (
	"fmt"
	"wirez/repo"
)

type IHelloService interface {
	HelloFoo()
	HelloBar()
	HelloBar2()
}

type HelloService struct {
	FooRepo  repo.IFooRepo
	BarRepo  repo.IBarRepo
	Bar2Repo repo.IBar2Repo
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

func (s *HelloService) HelloBar2() {
	fmt.Println("=== HelloService.HelloBar ===")
	s.Bar2Repo.Bar()

	str := "bar2"
	add, err := s.Bar2Repo.Add(&str)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(*add)
	}
}
