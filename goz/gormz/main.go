package main

import (
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gormz/internal"
	"gormz/internal/model"
)

var db *gorm.DB

func init() {
	dsn := "host=localhost user=demo password=demo dbname=demo port=3432 sslmode=disable TimeZone=Asia/Shanghai"
	gormConfig := &gorm.Config{
		TranslateError: true,
	}
	db, err := gorm.Open(postgres.Open(dsn), gormConfig)
	internal.CheckErr(err)
	// 迁移 schema
	model.AutoMigrate(db)
}

func main() {
	//// Create
	//db.Create(&model.Product{Code: "D42", Price: 100})
	//// Delete - 删除 product
	//var product model.Product
	//db.Delete(&product, 1)
	fmt.Println("end")
}
