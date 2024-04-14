# wirez

## wirez使用注意

### 1. inject BuildInjector: unused provider set "Set"

```go
func BuildInjector(ctx context.Context) (*Injector, func(), error) {
	wire.Build(
		initPgsql,
		initMongo,
		repo.Set,
		service.Set,
		wire.NewSet(wire.Struct(new(S), "*")),
		wire.NewSet(wire.Struct(new(Injector), "*")),
	)
	return new(Injector), nil, nil
}
```
在此中注入的所有对象，都要导出，在上述代码片段中，是将所有的都放在`Injector`中导出。