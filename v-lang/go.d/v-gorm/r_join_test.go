package main

import "testing"

func TestBarRepo_InitData(t *testing.T) {
	repo := BarRepo{db: db}
	//repo.InitData()
	repo.InnerJoin()
}
