package repo

import "github.com/google/wire"

var Set = wire.NewSet(
	provideFooRepo,
	//wire.Bind(new(IFooRepo), new(*FooRepo)),
)
