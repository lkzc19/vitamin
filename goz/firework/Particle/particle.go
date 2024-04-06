package Particle

import (
	"firework/firework"
	"gonum.org/v1/gonum/spatial/r2"
	"time"
)

type Particle struct {
	// 位置
	Pos r2.Vec
	// 速度
	Vel r2.Vec
	// 轨迹
	Trail []r2.Vec
	// `Particle`的生命周期
	Lifecycle Lifecycle
	// `Particle`自初始化以来的时间
	TimeElapsed time.Duration
	Config      Config
}

func (p Particle) IsDead() bool {
	return p.Lifecycle == DEAD
}

func (p Particle) reset() {
	p.Pos = p.Config.InitPos
	p.Vel = p.Config.InitVel
	for i := 0; i < p.Config.TrailLength; i++ {
		p.Trail[i] = p.Pos
	}
	p.Lifecycle = ALIVE
	p.TimeElapsed = 0
}

var VecDefault = 0.1

func (p Particle) Update(duration time.Duration, config *firework.Config) {
	const TIMESTEMP = 0.001
	p.TimeElapsed += duration
	p.Lifecycle = calcLifecycle(p.Config.LifeTime, p.TimeElapsed)
	var t = 0.
	for t < duration.Seconds() {
		//p.Vel += TIMESTEMP
		//foo := VecDefault * 10. * config.GravityScale
		//r2.Unit(p.Vel) * r2.

		//TIMESTEMP * (VecDefault*10.*config.GravityScale - p.Vel)
	}
	p.Trail = p.Trail[1:]
	p.Trail = append(p.Trail, p.Pos)
}

func calcLifecycle(lifeTime time.Duration, currentElapsed time.Duration) Lifecycle {
	var p = float32(currentElapsed.Milliseconds()) / float32(lifeTime.Milliseconds())
	if p < 0.4 {
		return ALIVE
	} else if p < 0.65 {
		return DECLINING
	} else if p < 1. {
		return DYING
	} else {
		return DEAD
	}
}
