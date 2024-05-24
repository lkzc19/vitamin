package main

import (
	"fmt"
	"gormz/internal"
	"gormz/internal/model"
)

func InsertTestData() {
	// Create
	DB.Create(&model.Product{Code: "D42", Price: 100})
	DB.Create(&model.Product{Code: "D43", Price: 100})
	DB.Create(&model.Product{Code: "D45", Price: 100})
	DB.Create(&model.Product{Code: "D46", Price: 100})
	DB.Create(&model.Product{Code: "D47", Price: 100})
	DB.Create(&model.Product{Code: "D48", Price: 100})
	DB.Create(&model.Product{Code: "D49", Price: 100})
	DB.Create(&model.Product{Code: "D50", Price: 100})
	DB.Create(&model.Product{Code: "D41", Price: 100})
	DB.Create(&model.Product{Code: "D42", Price: 100})
	DB.Create(&model.Product{Code: "D43", Price: 100})
}

func FindMax() {
	var maxPrice int
	if err = DB.Model(&model.Product{}).Select("MAX(price)").Scan(&maxPrice).Error; err != nil {
		internal.CheckErr(err)
	}
	fmt.Println("maxPrice:", maxPrice)
}
