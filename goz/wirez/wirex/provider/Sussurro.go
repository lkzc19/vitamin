package provider

import (
	"fmt"
	"wirez/common"
)

// Sussurro 测试无返回值
// 结果 无返回值会报错 `no return values` 可以没有`cleanup func`
func Sussurro() (common.Sussurro, error) {
	fmt.Printf("测试无cleanup\n")
	return "Sussurro", nil
}
