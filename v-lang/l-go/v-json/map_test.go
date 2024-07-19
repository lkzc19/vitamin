package main

import (
	"encoding/json"
	"fmt"
	"testing"
)

func TestMap(t *testing.T) {
	result := make(map[string]any)
	result["lineChart"] = map[string]any{
		"x": []string{"01-01", "01-02", "01-03", "01-04", "01-05"},
		"data": []map[string]any{
			{
				"name": "dau",
				"data": []int{4, 5, 7, 2, 9},
			},
			{
				"name": "dnu",
				"data": []int{3, 6, 1, 7, 8},
			},
		},
	}
	jsonStr, err := json.Marshal(result)
	checkErr(err)
	fmt.Println(string(jsonStr))
}
