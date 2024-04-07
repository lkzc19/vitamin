package wirex

import (
	"context"
	"fmt"
	"wirez/common"
)

func initPgsql(ctx context.Context) (common.PgsqlStr, func(), error) {
	fmt.Println("=== pgsql ===")
	fmt.Printf("=== %s ===\n", ctx.Value("foo"))

	return "pgsql", func() {
		fmt.Println("pgsql close...")
	}, nil
}

func initMongo(ctx context.Context) (common.MongoStr, func(), error) {
	fmt.Println("=== mongo ===")
	fmt.Printf("=== %s ===\n", ctx.Value("foo"))

	return "mongo", func() {
		fmt.Println("mongo close...")
	}, nil
}

func initDB(pgsql common.PgsqlStr, mongo common.MongoStr) (common.DBStr, func(), error) {
	fmt.Printf("测试将容器中的对象再注入到新对象中 \t %s %s\n", pgsql, mongo)

	return "db", func() {
		fmt.Println("db close...")
	}, nil
}
