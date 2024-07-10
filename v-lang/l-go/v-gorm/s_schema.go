package main

import (
	"fmt"
	"gorm.io/gorm"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

type SchemaService struct {
	db *gorm.DB
}

// AddColumn 使用Gorm的接口添加字段 但是必须是dst中有的字段才能添加成功
func (s SchemaService) AddColumn(field string) {
	if !s.db.Migrator().HasColumn(model.Event{}, field) {
		err = s.db.Migrator().AddColumn(model.Event{}, field)
		internal.CheckErr(err)
	}
}

// AddColumnBySql 使用SQL添加字段
func (s SchemaService) AddColumnBySql(field string, fieldType string) {
	if !s.db.Migrator().HasColumn(model.Event{}, field) {
		tn := model.Event{}.TableName()
		// 执行原生SQL语句新增表字段
		sql := fmt.Sprintf("ALTER TABLE %s ADD COLUMN %s %s", tn, field, fieldType)
		err = s.db.Exec(sql).Error
		internal.CheckErr(err)
	}
}

func (s SchemaService) HasColumn(field string) bool {
	return s.db.Migrator().HasColumn(model.Event{}, field)
}
