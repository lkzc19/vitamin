package main

import (
	"fmt"
	"github.com/casbin/casbin/v2"
	gormadapter "github.com/casbin/gorm-adapter/v3"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

var e *casbin.Enforcer

func init() {
	dsn := "host=localhost user=postgres password=123456789 dbname=demo port=5432 sslmode=disable TimeZone=Asia/Shanghai"
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})
	checkErr(err)

	adapter, err := gormadapter.NewAdapterByDB(db)
	checkErr(err)

	e, err = casbin.NewEnforcer("model.conf", adapter)
	checkErr(err)
}

func main() {
	err := e.LoadPolicy()
	checkErr(err)
	ok, err := e.Enforce("nahida", "data1", "read")
	checkErr(err)
	fmt.Println(ok)

	ok, err = e.Enforce("nahida", "data3", "read")
	checkErr(err)
	fmt.Println(ok)
}

func add() {
	_, err := e.AddPolicy("nahida", "data1", "read", "allow", "allow")
	checkErr(err)
	_, err = e.AddPolicy("nahida", "data2", "read", "allow", "allow")
	checkErr(err)
	_, err = e.AddPolicy("nahida", "data3", "read", "allow", "deny")
	checkErr(err)
}
