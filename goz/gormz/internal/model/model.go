package model

import (
	"gorm.io/gorm"
	"gormz/internal"
)

func AutoMigrate(db *gorm.DB) {
	err := db.AutoMigrate(Product{}, Foo{}, Bar{})
	internal.CheckErr(err)
}
