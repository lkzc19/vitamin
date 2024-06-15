package provider

import (
	"fmt"
	"wirez/common"
)

// DB 测试依赖能否顺利注入 以及依赖顺序是否有要求
func DB(pgsql common.PgsqlStr, mongo common.MongoStr) (common.DBStr, func(), error) {
	fmt.Printf("测试将容器中的对象再注入到新对象中 \t %s %s\n", pgsql, mongo)

	return "db", func() {
		fmt.Println("db close...")
	}, nil
}
