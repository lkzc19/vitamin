//go:build wireinject
// +build wireinject

package wirex

import (
	"context"
	"github.com/google/wire"
	"wirez/repo"
	"wirez/service"
)

func BuildInjector(ctx context.Context) (*Injector, func(), error) {
	wire.Build(
		initPgsql,
		initMongo,
		initDB,
		repo.Set,
		service.Set,
		wire.NewSet(wire.Struct(new(S), "*")),
		wire.NewSet(wire.Struct(new(Injector), "*")),
	)
	return new(Injector), nil, nil
}
