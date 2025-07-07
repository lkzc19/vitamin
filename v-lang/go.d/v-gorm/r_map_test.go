package main

import (
	"fmt"
	"testing"
	"time"
)

func TestMapInsert(t *testing.T) {
	var mapRepo = MapRepo{db: db}
	item := map[string]any{}
	item["name"] = map[string]any{}["name"]
	mapRepo.Insert(item)
}

func TestMapBatchInsert(t *testing.T) {
	var mapRepo = MapRepo{db: db}
	os := []string{"iOS", "Android", "Windows", "Mac"}
	var list []map[string]any
	for i := 0; i < 100; i++ {
		item := map[string]any{
			"name":                   "Buy",
			"prop_product_text":      os[i%len(os)],
			"prop_product_timestamp": time.Now(),
			"prop_product_numeric":   i,
			"prop_product_bool":      i%3 == 0,
		}
		list = append(list, item)
	}
	mapRepo.BatchInsert(list)
}

func TestBatchInsertBySql(t *testing.T) {
	var mapRepo = MapRepo{db: db}
	os := []string{"iOS", "Android", "Windows", "Mac"}
	var list []map[string]any
	for i := 0; i < 100; i++ {
		item := map[string]any{
			"name":                   "Buy",
			"prop_product_text":      os[i%len(os)],
			"prop_product_timestamp": time.Now(),
			"prop_product_numeric":   i,
			"prop_product_bool":      i%3 == 0,
		}
		list = append(list, item)
	}
	mapRepo.BatchInsertBySql(list)
}

func TestMapList(t *testing.T) {
	var mapRepo = MapRepo{db: db}
	list := mapRepo.List()

	for _, it := range list {
		fmt.Println(it)
	}
}
