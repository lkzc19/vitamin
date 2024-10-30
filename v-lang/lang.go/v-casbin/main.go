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
	dsn := "host=localhost user=vitamin password=vitamin dbname=vitamin port=3432 sslmode=disable TimeZone=Asia/Shanghai"
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: logger.Default.LogMode(logger.Info),
	})
	checkErr(err)

	adapter, err := gormadapter.NewAdapterByDB(db)
	checkErr(err)

	e, err = casbin.NewEnforcer("model_builtin_func.conf", adapter)
	checkErr(err)
	// 注意配置文件
	e.AddFunction("pingMatcher", pingMatchFunc)
}

func main() {
	err := e.LoadPolicy()
	checkErr(err)

	add()

	ok, err := e.Enforce("nahida", "/foo/1")
	checkErr(err)
	fmt.Println(ok)

	ok, err = e.Enforce("nahida", "/bar")
	checkErr(err)
	fmt.Println(ok)
}

func add() {
	_, err := e.AddPolicy("nahida", "/foo/:id")
	checkErr(err)
	_, err = e.AddPolicy("nahida", "/bar")
	checkErr(err)
	_, err = e.AddPolicy("hutao", "/bar")
	checkErr(err)
}
