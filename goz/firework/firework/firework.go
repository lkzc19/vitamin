package firework

import (
	"gonum.org/v1/gonum/spatial/r2"
	"time"
)

type Firework struct {
	// `Firework`初始化时的时间
	InitTime time.Time
	//
	SpawnAfter time.Duration
	//
	TimeElapsed time.Duration
	Center      r2.Vec
	Lifecycle   Lifecycle
}
