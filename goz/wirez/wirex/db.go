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
