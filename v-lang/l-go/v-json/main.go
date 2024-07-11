package main

import (
	"encoding/json"
	"fmt"
)

func main() {
	jsonStr := "{\"nahida\":\"纳西妲\",\"hutao\":{\"first\":\"hu\",\"second\":\"tao\"}}"
	var data map[string]any
	err := json.Unmarshal([]byte(jsonStr), &data)
	checkErr(err)

	fmt.Println(data)
	hutao := data["hutao"].(map[string]any)
	fmt.Println(hutao)
	fmt.Println(hutao["first"])
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
