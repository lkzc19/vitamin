package strcutx

import (
	"fmt"
	"testing"
)

func TestFoo(t *testing.T) {
	foo := Foo{items: make([]string, 0)}
	foo.Print()
	fmt.Println("=====")
	foo.Add()
	foo.Print()
}

func TestBar(t *testing.T) {
	bar := Bar{items: make([]string, 0)}
	bar.Print()
	fmt.Println("=====")
	bar.Add()
	bar.Print()
}
