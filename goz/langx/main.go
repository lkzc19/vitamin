package main

import (
	"fmt"
	mongo2 "lang/adapter/mongo"
	"lang/config/mongo"
	r "lang/repo"
	m "lang/repo/mongo"
	p "lang/repo/pgsql"
)

func main() {
	fmt.Println("hello golang")

	m.Foo()
	p.Bar()
	r.Repo()
	mongo.Foo()
	mongo2.Foo()
}
