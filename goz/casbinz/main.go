package main

import (
	"github.com/casbin/casbin/v2"
	gormadapter "github.com/casbin/gorm-adapter/v3"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"strconv"
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

	f := func() {
		err := e.LoadPolicy()
		checkErr(err)

		testBigData()

		err = e.SavePolicy()
		checkErr(err)
	}

	t(f)
}

// testBigData 测试 casbin gorm 插入大量数据
func testBigData() {
	for i := 0; i < 50000; i++ {
		// 相同的策略 casbin 内部会做去重
		_, err := e.AddPolicy("bar"+strconv.Itoa(i), "data1", "read")
		checkErr(err)
	}
}
