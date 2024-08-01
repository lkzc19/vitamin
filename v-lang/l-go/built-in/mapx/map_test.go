package mapx

import (
	"fmt"
	"testing"
)

func Test1(t *testing.T) {
	var m map[string]string
	v, ok := m["f00"]
	fmt.Println(v, ok)
}

func Test2(t *testing.T) {
	m := make(map[string][]string)
	v, ok := m["f00"]
	fmt.Println(v, ok)
	v = append(v, "bar")
	v1, ok := m["f00"]
	fmt.Println(v1, ok)
	m["f00"] = v // 必须要初始化
	v2, ok := m["f00"]
	fmt.Println(v2, ok)
}
