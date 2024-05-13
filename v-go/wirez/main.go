package main

import (
	"context"
	"wirez/wirex"
)

func main() {
	ctx := context.Background()
	ctx = context.WithValue(ctx, "foo", "bar")

	injector, _, err := wirex.BuildInjector(ctx)
	if err != nil {
		panic(err)
	}

	injector.S.FooService.HelloFoo()
	injector.S.FooService.HelloBar()
	injector.S.FooService.HelloBar2()
}
