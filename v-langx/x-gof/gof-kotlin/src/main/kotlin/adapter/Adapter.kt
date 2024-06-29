package adapter

import kotlin.math.sqrt

/**
 * 不要为了使用设计模式而去使用设计模式
 * 适配器是为了`不修改`或是想`复用`原先代码的情况下而被迫使用的一种设计模式
 *
 * 比如想要复用 RoundHole.fits 的逻辑 且不想修改 那么就使用适配器
 */

// 圆孔
class RoundHole(private val radius: Int) {
    fun fits(peg: RoundPeg) = radius >= peg.radius
}

// 圆钉
open class RoundPeg(val radius: Int)

// 方钉
class SquarePeg(val width: Int)

// 方钉的适配器
class SquarePegAdapter(peg: SquarePeg) : RoundPeg((peg.width * sqrt(2.0) / 2).toInt())
