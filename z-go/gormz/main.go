package main

import (
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gormz/internal"
	"gormz/internal/model"
)

var DB *gorm.DB
var err error

func init() {
	dsn := "host=localhost user=demo password=demo dbname=demo port=3432 sslmode=disable TimeZone=Asia/Shanghai"
	gormConfig := &gorm.Config{
		TranslateError: true,
	}
	DB, err = gorm.Open(postgres.Open(dsn), gormConfig)
	internal.CheckErr(err)
	// 迁移 schema
	model.AutoMigrate(DB)
}

func main() {
	//InsertTestData()
	FindMax()
	fmt.Println("=== End ===")
}
