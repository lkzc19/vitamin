package main

import "strconv"

func test() {
	err := e.LoadPolicy()
	checkErr(err)
	f := func() {
		testBigData2()
	}
	err = e.SavePolicy()
	checkErr(err)

	t(f)
}

// testBigData 测试 casbin gorm 插入大量数据
// 使用 AddPolicy
func testBigData() {
	for i := 0; i < 200000; i++ {
		_, err := e.AddPolicy("million"+strconv.Itoa(i), "data1", "read")
		checkErr(err)
	}
}

// testBigData 测试 casbin gorm 插入大量数据
// 使用 AddPolicy
func testBigData2() {
	pSet := make([][][]string, 0)

	p := make([][]string, 0)
	for i := 0; i < 200000; i++ {
		if len(p) == 1000 {
			pSet = append(pSet, p)
			p = make([][]string, 0)
		}
		p = append(p, []string{"millionx" + strconv.Itoa(i), "data1", "read"})
	}
	if len(p) > 0 {
		pSet = append(pSet, p)
	}

	for _, it := range pSet {
		_, err := e.AddPolicies(it)
		checkErr(err)
	}
}
