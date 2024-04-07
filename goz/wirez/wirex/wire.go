//go:build wireinject
// +build wireinject

package wirex

import (
	"context"
	"github.com/google/wire"
	"wirez/repo"
)

func BuildInjector(ctx context.Context) (*Injector, func(), error) {
	wire.Build(
		initPgsql,
		initMongo,
		wire.NewSet(wire.Struct(new(Injector), "*")),
		repo.Set,
	)
	return new(Injector), nil, nil
}
