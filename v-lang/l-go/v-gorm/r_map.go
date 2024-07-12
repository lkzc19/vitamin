package main

import (
	"fmt"
	"gorm.io/gorm"
	"strings"
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

func (r MapRepo) BatchInsertBySql(list []map[string]any) {
	for _, item := range list {
		var columns []string
		var values []any

		for column, value := range item {
			columns = append(columns, column)
			values = append(values, value)
		}

		query := fmt.Sprintf(
			"INSERT INTO %s (%s) VALUES (%s)",
			model.Event{}.TableName(),
			strings.Join(columns, ","),
			generatePlaceholders(len(columns)),
		)

		err = r.db.Exec(query, values...).Error
		internal.CheckErr(err)
	}
}

func generatePlaceholders(count int) string {
	placeholders := make([]string, count)
	for i := 0; i < count; i++ {
		placeholders[i] = "?"
	}
	return strings.Join(placeholders, ",")
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
