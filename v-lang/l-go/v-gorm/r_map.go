package main

import (
	"gorm.io/gorm"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

type MapRepo struct {
	db *gorm.DB
}

func (r MapRepo) Insert(item map[string]any) {
	err = r.db.Table(model.Event{}.TableName()).Create(item).Error
	internal.CheckErr(err)
}

func (r MapRepo) BatchInsert(list []map[string]any) {
	err = r.db.Table(model.Event{}.TableName()).CreateInBatches(list, 100).Error
	internal.CheckErr(err)
}

func (r MapRepo) List() []map[string]any {
	// 查询数据
	var list []map[string]any
	err = r.db.Table(model.Event{}.TableName()).Where("name = ?", "$AppViewScreen").Find(&list).Error
	internal.CheckErr(err)
	return list
}

func (r MapRepo) List1() []map[string]any {
	// 查询数据
	var list []map[string]any
	err = r.db.Table(model.Event{}.TableName()).Where("name = ?", "Buy").Find(&list).Error
	internal.CheckErr(err)
	return list
}
