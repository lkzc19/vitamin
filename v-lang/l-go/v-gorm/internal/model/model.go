package model

import (
	"gorm.io/gorm"
	"v-gorm/internal"
)

func AutoMigrate(db *gorm.DB) {
	createIfAbsent(db, Event{})
	err := db.AutoMigrate(
		Foo{},
		Bar{},
	)
	internal.CheckErr(err)
}

func createIfAbsent(db *gorm.DB, dst ...any) {
	for _, it := range dst {
		if !db.Migrator().HasTable(it) {
			err := db.Migrator().CreateTable(it)
			internal.CheckErr(err)
		}
	}
}
