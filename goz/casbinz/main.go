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
	err := e.LoadPolicy()
	checkErr(err)
	f := func() {
		testBigData2()
	}
	err = e.SavePolicy()
	checkErr(err)

	t(f)
}

// testBigData 测试 casbin gorm 插入大量数据
// 使用 AddPolicy
func testBigData() {
	for i := 0; i < 200000; i++ {
		_, err := e.AddPolicy("million"+strconv.Itoa(i), "data1", "read")
		checkErr(err)
	}
}

// testBigData 测试 casbin gorm 插入大量数据
// 使用 AddPolicy
func testBigData2() {
	pSet := make([][][]string, 0)

	p := make([][]string, 0)
	for i := 0; i < 200000; i++ {
		if len(p) == 1000 {
			pSet = append(pSet, p)
			p = make([][]string, 0)
		}
		p = append(p, []string{"millionx" + strconv.Itoa(i), "data1", "read"})
	}
	if len(p) > 0 {
		pSet = append(pSet, p)
	}

	for _, it := range pSet {
		_, err := e.AddPolicies(it)
		checkErr(err)
	}
}
