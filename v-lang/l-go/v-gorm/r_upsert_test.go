package main

import (
	"gorm.io/gorm"
	"testing"
	"v-gorm/internal/model"
)

func TestUpsertRepo_Upsert(t *testing.T) {
	var upsertRepo = UpsertRepo{db: DB}
	foo := model.Foo{
		Model: gorm.Model{ID: 1},
		Name:  "foo",
	}
	upsertRepo.Upsert(foo)
	foo.Name = "bar"
	upsertRepo.Upsert(foo)
}

func TestUpsertRepo_InsertIfAbsent(t *testing.T) {
	var upsertRepo = UpsertRepo{db: DB}
	foo := model.Foo{
		Name: "hutao",
	}
	id := upsertRepo.InsertIfAbsent(foo)
	foo.ID = id
	foo.Name = "nahida"
	upsertRepo.InsertIfAbsent(foo)
}
