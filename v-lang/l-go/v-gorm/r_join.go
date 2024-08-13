package main

import (
	"fmt"
	"gorm.io/gorm"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

type BarRepo struct {
	db *gorm.DB
}

func (r BarRepo) InitData() {
	barList := []*model.Bar{
		{Name: "bar1", Num: 18},
		{Name: "bar2", Num: 19},
		{Name: "foo1", Num: 19},
		{Name: "foo2", Num: 19},
	}
	result := r.db.Create(barList)
	internal.CheckErr(result.Error)
	fmt.Printf("bar 插入数量: %d", result.RowsAffected)
}

type result struct {
	Name string
	Num  int64
}

func (r BarRepo) InnerJoin() {
	var items []result
	err = r.db.Model(&model.Bar{}).
		//Select("").
		Joins("JOIN v_foo ON v_foo.name = v_bar.name").
		Find(&items).Error
	internal.CheckErr(err)
	for _, it := range items {
		fmt.Println(it)
	}
}
