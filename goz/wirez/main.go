package main

import (
	"context"
	"fmt"
	"wirez/wirex"
)

func main() {
	fmt.Println("=== hello world ===")

	ctx := context.Background()
	ctx = context.WithValue(ctx, "foo", "bar")

	injector, _, err := wirex.BuildInjector(ctx)
	if err != nil {
		panic(err)
	}

	injector.S.FooService.Hello()
}
