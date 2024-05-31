package main

import (
	"fmt"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"v-gorm/internal"
	"v-gorm/internal/model"
)

var DB *gorm.DB
var err error

func init() {
	dsn := "host=localhost user=vitamin password=vitamin dbname=vitamin port=3432 sslmode=disable TimeZone=Asia/Shanghai"
	gormConfig := &gorm.Config{
		TranslateError: true,
	}
	DB, err = gorm.Open(postgres.Open(dsn), gormConfig)
	internal.CheckErr(err)
	// 迁移 schema
	model.AutoMigrate(DB)
}

func main() {
	fmt.Println("hello gorm")
	var transactionRepo = TransactionRepo{DB: DB}
	transactionRepo.T2()
}
