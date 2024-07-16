package main

import (
	"fmt"
	"testing"
)

func TestLogicDeleteRepo_SoftDelete(t *testing.T) {
	var logicDeleteRepo = LogicDeleteRepo{db: db}
	logicDeleteRepo.InitData()
	logicDeleteRepo.SoftDelete("foo1")
	foo := logicDeleteRepo.Select("foo1")
	fmt.Println(foo)
	fmt.Print("===============")
	softDelete := logicDeleteRepo.SelectSoftDelete("foo1")
	fmt.Println(softDelete)
}

func TestLogicDeleteRepo_HardDelete(t *testing.T) {
	var logicDeleteRepo = LogicDeleteRepo{db: db}
	logicDeleteRepo.InitData()
	logicDeleteRepo.HardDelete("foo1")
}
