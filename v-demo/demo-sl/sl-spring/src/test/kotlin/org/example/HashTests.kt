package org.example

import com.goncalossilva.murmurhash.MurmurHash3
import kotlin.test.Test

class HashTests {

    @Test
    fun hash() {
        val hash32x86 = MurmurHash3().hash32x86("hello, world!".encodeToByteArray())
        println(hash32x86)
    }

    @Test
    fun `10 to 62`() {
        var hash32x86 = MurmurHash3().hash32x86("https://flowus.cn/veal/share/3306b991-e1e3-4c92-9105-95abf086ae4e".encodeToByteArray())

        var remainder = hash32x86 % 62u
        hash32x86 /= 62u
        var str = "${base62Map[remainder]}"
        while (hash32x86 != 0u) {
            remainder = hash32x86 % 62u
            hash32x86 /= 62u
            str += base62Map[remainder]
        }
        println(str.reversed())
    }

    /**
     * 计算还有问题
     */
    @Test
    fun `perf 10 to 62`() {
        val hash = MurmurHash3().hash32x86("https://flowus.cn/veal/share/3306b991-e1e3-4c92-9105-95abf086ae4e".encodeToByteArray())
        var remainder = hash
        var hashValue = hash / 62u
        val stringBuilder = StringBuilder()

        while (hashValue != 0u) {
            remainder = hashValue and 61u
            hashValue = hashValue shr 6 // 使用位运算代替除法操作
            stringBuilder.append(base62Map[remainder])
        }

        stringBuilder.append(base62Map[remainder])

        println(stringBuilder.reverse().toString())
    }
}