package provider

import (
	"context"
	"fmt"
	"wirez/common"
)

// Pgsql 测试ctx是否能正常传入
func Pgsql(ctx context.Context) (common.PgsqlStr, func(), error) {
	fmt.Println("=== pgsql ===")
	fmt.Printf("=== %s ===\n", ctx.Value("foo"))

	return "pgsql", func() {
		fmt.Println("pgsql close...")
	}, nil
}
