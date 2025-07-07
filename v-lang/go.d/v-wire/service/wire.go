package service

import "github.com/google/wire"

var Set = wire.NewSet(
	wire.Struct(new(HelloService), "*"),
)
