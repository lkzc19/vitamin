package firework

type Config struct {
	// 重力
	GravityScale float32
	// 空气阻力
	AirScale float32
	// 其他的力量
	OtherScale func()
	// `Particle`颜色渐变梯度的浮点值
	GradientScale func(float32) float32
	// 设置烟花是否有颜色渐变
	EnableGradient bool
}
