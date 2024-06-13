package model

import (
	"gorm.io/gorm"
	"v-gorm/internal"
)

func AutoMigrate(db *gorm.DB) {
	err := db.AutoMigrate(
		Foo{},
		Bar{},
	)
	internal.CheckErr(err)
}
