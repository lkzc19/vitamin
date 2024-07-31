package org.example.param

data class PageParam(
    val pageC: Int,
    val pageS: Int,
) {
    val limit: Int get() {
        return if (pageS >= 0) pageS else 10
    }
    val offset: Int get() {
        ((pageC - 1) * pageS).let {
            return if (it < 0) 0 else it
        }
    }
    fun pageT(itemCount: Int): Int {
        return (itemCount / this.pageS) + if ((itemCount % this.pageS) == 0) 0 else 1
    }
}
