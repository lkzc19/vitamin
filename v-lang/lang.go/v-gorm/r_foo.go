package main

import (
	"fmt"
	"gorm.io/gorm"
	"time"
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
	err = r.db.Table(model.Foo{}.TableName()).
		Select("to_char(created_at, 'YYYY-MM') as month, COUNT(DISTINCT name) as count").
		Group("month").
		Order("month desc").
		Find(&result).Error
	internal.CheckErr(err)
	return result
}

func (r FooRepo) CountByDay() []map[string]any {
	var result []map[string]any
	err = r.db.Table(model.Foo{}.TableName()).
		Select("to_char(created_at, 'YYYY-MM-DD') as day, COUNT(DISTINCT name) as count").
		Group("day").
		Order("day desc").
		Find(&result).Error
	internal.CheckErr(err)
	return result
}

func (r FooRepo) SumByDay() []map[string]any {
	var result []map[string]any
	err = r.db.Table(model.Foo{}.TableName()).
		Select("to_char(created_at, 'YYYY-MM-DD') as day, SUM(num) as sum").
		Group("day").
		Order("day desc").
		Find(&result).Error
	internal.CheckErr(err)
	return result
}

// Where 测试中途加入条件
func (r FooRepo) Where() []map[string]any {
	var result []map[string]any

	tx := r.db.Table(model.Foo{}.TableName()).
		Select("to_char(created_at, 'YYYY-MM-DD') as day, COUNT(DISTINCT name) as count").
		Group("day").
		Order("day desc")

	err = tx.Where("name = ?", "foo1").Find(&result).Error

	internal.CheckErr(err)
	return result
}

func (r FooRepo) ListByTime(days int) []map[string]any {
	startDate := time.Now().AddDate(0, 0, days)
	var result []map[string]any
	err = r.db.Table(model.Foo{}.TableName()).
		Where("created_at >= ?", startDate).
		Find(&result).Error
	internal.CheckErr(err)
	return result
}
