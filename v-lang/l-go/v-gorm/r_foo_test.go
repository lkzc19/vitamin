package main

import (
	"fmt"
	"testing"
)

func TestFooRepo_Count(t *testing.T) {
	var fooRepo = FooRepo{db: db}
	fooRepo.InitData()
	fmt.Println("count: ", fooRepo.Count())
}

func TestFooRepo_CountByMonth(t *testing.T) {
	var fooRepo = FooRepo{db: db}
	for k, v := range fooRepo.CountByMonth() {
		fmt.Println(k, v)
	}
}

func TestFooRepo_CountByDay(t *testing.T) {
	var fooRepo = FooRepo{db: db}
	for k, v := range fooRepo.CountByDay() {
		fmt.Println(k, v)
	}
}

func TestFooRepo_Where(t *testing.T) {
	var fooRepo = FooRepo{db: db}
	for k, v := range fooRepo.Where() {
		fmt.Println(k, v)
	}
}
