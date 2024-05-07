package internal

import (
	"fmt"
	"github.com/lkzc19/nlu/mapx"
	"github.com/lkzc19/nlu/slicex"
	am "langz/internal/adapter/mongo"
	cm "langz/internal/config/mongo"
	r "langz/internal/repo"
	rm "langz/internal/repo/mongo"
	rp "langz/internal/repo/pgsql"
	"testing"
)

func TestPkg(t *testing.T) {
	rm.Foo()
	rp.Bar()
	r.Repo()
	cm.Foo()
	am.Foo()
}

func TestNlu(t *testing.T) {
	s1 := []string{"a", "b", "c"}
	s2 := []string{"c", "d", "e"}

	fmt.Println(slicex.Subtract(s1, s2))
	fmt.Println(slicex.Intersect(s1, s2))
	fmt.Println(slicex.Union(s1, s2))

	m := map[string]int{"a": 1, "b": 2, "c": 3}

	fmt.Println(mapx.Keys(m))
	fmt.Println(mapx.Values(m))
}
