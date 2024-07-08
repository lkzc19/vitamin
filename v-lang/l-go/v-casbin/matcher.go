package main

import "fmt"

/*
添加自定义匹配函数 https://casbin.org/zh/docs/function
*/

//=========== 测试是否可用 ===========

func pingMatcher(rPath, pPath string) bool {
	fmt.Println("rPath: ", rPath, "pPath: ", pPath)
	return true
}

func pingMatchFunc(args ...interface{}) (interface{}, error) {
	name1 := args[0].(string)
	name2 := args[1].(string)

	return pingMatcher(name1, name2), nil
}
