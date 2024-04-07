package wirex

import (
	"wirez/common"
	"wirez/service"
)

type Injector struct {
	Pgsql common.PgsqlStr
	Mongo common.MongoStr
	S     S
}

type S struct {
	FooService service.FooService
}
