//go:build wireinject
// +build wireinject

package wirex

import (
	"context"
	"github.com/google/wire"
	"wirez/repo"
	"wirez/service"
	"wirez/wirex/initialize"
)

func BuildInjector(ctx context.Context) (*Injector, func(), error) {
	wire.Build(
		initialize.InitPgsql,
		initialize.InitMongo,
		initialize.InitDB,
		initialize.InitSussurro,
		repo.Set,
		service.Set,
		wire.NewSet(wire.Struct(new(S), "*")),
		wire.NewSet(wire.Struct(new(Injector), "*")),
	)
	return new(Injector), nil, nil
}
