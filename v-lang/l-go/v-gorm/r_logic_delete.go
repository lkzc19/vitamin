package main

import (
	"errors"
	"fmt"
	"gorm.io/gorm"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

type LogicDeleteRepo struct {
	db *gorm.DB
}

func (r LogicDeleteRepo) InitData() {
	fooList := []*model.Foo{
		{Name: "foo1", Num: 18},
		{Name: "foo2", Num: 19},
	}
	result := r.db.Create(fooList)
	internal.CheckErr(result.Error)
	fmt.Printf("foo 插入数量: %d", result.RowsAffected)
}

func (r LogicDeleteRepo) SoftDelete(name string) {
	err = r.db.Where("Name = ?", name).Delete(&model.Foo{}).Error
	internal.CheckErr(err)
}

func (r LogicDeleteRepo) HardDelete(name string) {
	err = r.db.Unscoped().Where("Name = ?", name).Delete(&model.Foo{}).Error
	internal.CheckErr(err)
}

func (r LogicDeleteRepo) Select(name string) *model.Foo {
	var foo *model.Foo
	err = db.Where("Name = ?", name).First(&foo).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil
	} else {
		internal.CheckErr(err)
	}
	return foo
}

func (r LogicDeleteRepo) SelectSoftDelete(name string) *model.Foo {
	var foo *model.Foo
	err = db.Unscoped().Where("Name = ?", name).First(&foo).Error
	if errors.Is(err, gorm.ErrRecordNotFound) {
		return nil
	} else {
		internal.CheckErr(err)
	}
	return foo
}
