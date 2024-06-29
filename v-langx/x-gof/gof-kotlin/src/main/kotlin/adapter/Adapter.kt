package adapter

import kotlin.math.sqrt

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
