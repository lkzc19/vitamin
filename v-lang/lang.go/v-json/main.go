package main

import (
	"encoding/json"
	"fmt"
	"reflect"
)

func main() {
	jsonStr := `{
		"nahida": "纳西妲",
		"hutao": {
			"first": "hu",
			"second": "tao"
		},
		"time": 6.00005
	}`
	var data map[string]any
	err := json.Unmarshal([]byte(jsonStr), &data)
	checkErr(err)

	fmt.Println(jsonStr)
	fmt.Println(data)
	hutao := data["hutao"].(map[string]any)
	fmt.Println(hutao)
	fmt.Println(hutao["first"])

	of := reflect.TypeOf(data["time"])
	fmt.Println(of)
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
