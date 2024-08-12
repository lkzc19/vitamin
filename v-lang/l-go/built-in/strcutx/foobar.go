package strcutx

import "fmt"

type Foo struct {
	items []string
}

func (r Foo) Add() {
	r.items = append(r.items, "B")
	r.items = append(r.items, "C")
}

func (r Foo) Print() {
	fmt.Println(r.items)
}

// ================================================

type Bar struct {
	items []string
}

func (r *Bar) Add() {
	r.items = append(r.items, "B")
	r.items = append(r.items, "C")
}

func (r *Bar) Print() {
	fmt.Println(r.items)
}
