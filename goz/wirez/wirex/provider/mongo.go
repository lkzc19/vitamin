package provider

import (
	"context"
	"fmt"
	"wirez/common"
)

// Mongo 测试返回的类型是否可以相同
// 结果 不能相同 如果有相同的需要 如common包下另起类型
func Mongo(_ context.Context) (common.MongoStr, func(), error) {
	return "mongo", func() {
		fmt.Println("mongo close...")
	}, nil
}
