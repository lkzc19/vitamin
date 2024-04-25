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

### 2. inject BuildInjector: no provider found for wirez/repo.BarRepo ...

全部报错如下:

```bash
wire: /Users/lkzc19/Projects/self/demo/goz/wirez/wirex/wire.go:13:1: inject BuildInjector: no provider found for wirez/repo.BarRepo
        needed by wirez/service.HelloService in provider set "Set" (/Users/lkzc19/Projects/self/demo/goz/wirez/service/wire.go:5:11)
        needed by wirez/wirex.S in provider set (/Users/lkzc19/Projects/self/demo/goz/wirez/wirex/wire.go:21:3)
        needed by *wirez/wirex.Injector in provider set (/Users/lkzc19/Projects/self/demo/goz/wirez/wirex/wire.go:22:3)
wire: wirez/wirex: generate failed
wire: at least one generate failure
make: *** [wirex] Error 1
```

是因为`repo.BarRepo`使用`wire.Bind`interface绑定struct，但是`service.HelloService`使用的是struct。

```go
// repo.BarRepo的注入方式如下
var Set = wire.NewSet(
    ProvideBarRepo,
    wire.Bind(new(IBarRepo), new(*BarRepo)),
)

type HelloService struct {
	FooRepo repo.IFooRepo
	// 此处使用的是struct，但是应该使用的是repo.IBarRepo
	BarRepo repo.BarRepo
}
```