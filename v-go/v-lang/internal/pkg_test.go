package internal

import (
	"fmt"
	"github.com/lkzc19/nlu/mapx"
	"github.com/lkzc19/nlu/slicex"
	"testing"
	am "z-lang/internal/adapter/mongo"
	cm "z-lang/internal/config/mongo"
	r "z-lang/internal/repo"
	rm "z-lang/internal/repo/mongo"
	rp "z-lang/internal/repo/pgsql"
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
