package adapter

fun main() {
    val hole = RoundHole(5)
    val roundPeg = RoundPeg(5)
    println(hole.fits(roundPeg))

    val smallSquarePeg = SquarePeg(5)
    val largeSquarePeg = SquarePeg(10)
    // 类型不一致无法编译
//    println(hole.fits(smallSquarePeg))

    val smallSquarePegAdapter = SquarePegAdapter(smallSquarePeg)
    val largeSquarePegAdapter = SquarePegAdapter(largeSquarePeg)
    println(hole.fits(smallSquarePegAdapter)) // true
    println(hole.fits(largeSquarePegAdapter)) // false
}