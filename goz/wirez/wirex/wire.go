//go:build wireinject
// +build wireinject

package wirex

import (
	"context"
	"github.com/google/wire"
	"wirez/repo"
	"wirez/service"
	"wirez/wirex/provider"
)

func BuildInjector(ctx context.Context) (*Injector, func(), error) {
	// 就算有依赖关系也没有顺序要求
	wire.Build(
		repo.Set,
		provider.Pgsql,
		provider.DB,
		provider.Mongo,
		provider.Sussurro,
		service.Set,
		wire.NewSet(wire.Struct(new(S), "*")),
		wire.NewSet(wire.Struct(new(Injector), "*")),
	)
	return new(Injector), nil, nil
}
