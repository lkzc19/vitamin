package main

import (
	"fmt"
	"gorm.io/gorm"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

type FooRepo struct {
	db *gorm.DB
}

func (r FooRepo) InitData() {
	fooList := []*model.Foo{
		{Name: "foo1", Num: 18},
		{Name: "foo2", Num: 19},
	}
	result := r.db.Create(fooList)
	internal.CheckErr(result.Error)
	fmt.Printf("foo 插入数量: %d", result.RowsAffected)
}

func (r FooRepo) Count() int64 {
	var count int64
	err = r.db.Model(&model.Foo{}).Distinct("name").Count(&count).Error
	internal.CheckErr(err)
	return count
}

func (r FooRepo) CountByMonth() []map[string]any {
	var result []map[string]any
	err := r.db.Table(model.Foo{}.TableName()).
		Select("to_char(created_at, 'YYYY-MM') as month, COUNT(DISTINCT name) as count").
		Group("month").
		Order("month desc").
		Find(&result).Error
	internal.CheckErr(err)
	return result
}

func (r FooRepo) CountByDay() []map[string]any {
	var result []map[string]any
	err := r.db.Table(model.Foo{}.TableName()).
		Select("to_char(created_at, 'YYYY-MM-DD') as day, COUNT(DISTINCT name) as count").
		Group("day").
		Order("day desc").
		Find(&result).Error
	internal.CheckErr(err)
	return result
}
