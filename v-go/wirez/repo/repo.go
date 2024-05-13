package repo

type Repo[T any] interface {
	C[T]
}

type C[T any] interface {
	Add(entity *T) (*T, error)
}
