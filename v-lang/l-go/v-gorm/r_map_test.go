package main

import (
	"fmt"
	"testing"
	"time"
)

func TestMapInsert(t *testing.T) {
	var mapRepo = MapRepo{db: DB}
	item := map[string]any{
		"name": "$AppViewScreen",
		//"prop_sd_os":      "30",
		"prop_order_time": time.Now(),
		//"prop_stock_time": time.Now(),
	}
	mapRepo.Insert(item)
}

func TestMapBatchInsert(t *testing.T) {
	var mapRepo = MapRepo{db: DB}
	os := []string{"iOS", "Android", "Windows", "Mac"}
	var list []map[string]any
	for i := 0; i < 100; i++ {
		item := map[string]any{
			"name":               "Buy",
			"prop_sd_os":         os[i%len(os)],
			"prop_product_count": i,
		}
		list = append(list, item)
	}
	mapRepo.BatchInsert(list)
}

func TestMapList(t *testing.T) {
	var mapRepo = MapRepo{db: DB}
	list := mapRepo.List1()

	for _, it := range list {
		fmt.Println(it)
	}
}

func TestMapList1(t *testing.T) {
	var mapRepo = MapRepo{db: DB}
	list := mapRepo.List1()

	count := 0.0
	for _, it := range list {
		count += it["prop_product_count"].(float64)
	}
	fmt.Println("商品总数: ", count)
}
