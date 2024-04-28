package main

import (
	"github.com/casbin/casbin/v2"
	gormadapter "github.com/casbin/gorm-adapter/v3"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func main() {
	dsn := "host=localhost user=postgres password=123456789 dbname=demo port=5432 sslmode=disable TimeZone=Asia/Shanghai"
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	checkErr(err)

	adapter, err := gormadapter.NewAdapterByDB(db)
	checkErr(err)

	e, err := casbin.NewEnforcer("model.conf", adapter)
	checkErr(err)

	err = e.LoadPolicy()
	checkErr(err)

	// 相同的策略 casbin 内部会做去重
	_, err = e.AddPolicy("alice", "data1", "read")
	_, err = e.AddPolicy("alice", "data1", "read")
	_, err = e.AddPolicy("alice", "data1", "write")
	checkErr(err)

	err = e.SavePolicy()
	checkErr(err)
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
