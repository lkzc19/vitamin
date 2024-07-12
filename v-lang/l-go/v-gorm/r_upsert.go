package main

import (
	"gorm.io/gorm"
	"gorm.io/gorm/clause"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

type UpsertRepo struct {
	db *gorm.DB
}

func (r UpsertRepo) Upsert(foo model.Foo) {
	err = r.db.Clauses(clause.OnConflict{
		// 必须是唯一键
		Columns:   []clause.Column{{Name: "id"}},
		DoUpdates: clause.AssignmentColumns([]string{"name"}),
	}).Create(&foo).Error
	internal.CheckErr(err)
}

// InsertIfAbsent 不存在则插入
func (r UpsertRepo) InsertIfAbsent(foo model.Foo) uint {
	err = r.db.Clauses(clause.OnConflict{DoNothing: true}).Create(&foo).Error
	internal.CheckErr(err)
	return foo.ID
}
