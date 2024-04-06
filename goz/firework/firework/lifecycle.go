package firework

type Lifecycle = string

const (
	WAITING Lifecycle = "WAITING"
	ALIVE   Lifecycle = "ALIVE"
	GONE    Lifecycle = "GONE"
)
