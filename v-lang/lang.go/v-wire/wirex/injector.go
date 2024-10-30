package wirex

import (
	"wirez/common"
	"wirez/service"
)

type Injector struct {
	Pgsql    common.PgsqlStr
	Mongo    common.MongoStr
	DB       common.DBStr
	Sussurro common.Sussurro
	S        S
}

type S struct {
	FooService service.HelloService
}
