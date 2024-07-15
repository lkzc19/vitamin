package main

import (
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
	fmt.Println("foo 插入数量: $d", result.RowsAffected)
}

func (r LogicDeleteRepo) SoftDelete(name string) {
	err = r.db.Where("Name = ?", name).Delete(&model.Foo{}).Error
	internal.CheckErr(err)
}

func (r LogicDeleteRepo) HardDelete(name string) {
	err = r.db.Unscoped().Where("Name = ?", name).Delete(&model.Foo{}).Error
	internal.CheckErr(err)
}
