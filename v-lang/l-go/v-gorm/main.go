package main

import (
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

var db *gorm.DB
var err error

func init() {
	dsn := "host=localhost user=vitamin password=vitamin dbname=vitamin port=3432 sslmode=disable TimeZone=Asia/Shanghai"
	gormConfig := &gorm.Config{
		Logger:         logger.Default.LogMode(logger.Info),
		TranslateError: true,
	}
	db, err = gorm.Open(postgres.Open(dsn), gormConfig)
	internal.CheckErr(err)
	// 迁移 schema
	model.AutoMigrate(db)
}

func main() {
	fmt.Println("hello gorm")
}
