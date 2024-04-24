package repo

import "github.com/google/wire"

var Set = wire.NewSet(
	ProvideFooRepo,
	wire.Bind(new(IFooRepo), new(*FooRepo)),
	ProvideBarRepo,
	wire.Bind(new(IBarRepo), new(*BarRepo)),
)
