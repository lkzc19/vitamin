package main

import (
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gormz/internal/model"
)

func main() {
	dsn := "host=localhost user=postgres password=123456789 dbname=demo port=5432 sslmode=disable TimeZone=Asia/Shanghai"
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}
	// 迁移 schema
	err = db.AutoMigrate(&model.Product{})
	if err != nil {
		return
	}
}
