package Particle

import (
	"gonum.org/v1/gonum/spatial/r2"
	"time"
)

type Config struct {
	InitPos     r2.Vec
	InitVel     r2.Vec
	TrailLength int
	// `Particle`初始化到它死亡的时间
	LifeTime time.Duration
	Color    Colorx
}

type Colorx struct {
	R, G, B uint8
}
