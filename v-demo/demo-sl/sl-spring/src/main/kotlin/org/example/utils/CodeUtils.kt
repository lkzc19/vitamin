package org.example.utils

import com.goncalossilva.murmurhash.MurmurHash3

object CodeUtils {

    private val hashUtils = MurmurHash3()

    private val base62 = mapOf(
        0u to '0', 1u to '1', 2u to '2', 3u to '3', 4u to '4',
        5u to '5', 6u to '6', 7u to '7', 8u to '8', 9u to '9',
        10u to 'a', 11u to 'b', 12u to 'c', 13u to 'd', 14u to 'e',
        15u to 'f', 16u to 'g', 17u to 'h', 18u to 'i', 19u to 'j',
        20u to 'k', 21u to 'l', 22u to 'm', 23u to 'n', 24u to 'o',
        25u to 'p', 26u to 'q', 27u to 'r', 28u to 's', 29u to 't',
        30u to 'u', 31u to 'v', 32u to 'w', 33u to 'x', 34u to 'y',
        35u to 'z', 36u to 'A', 37u to 'B', 38u to 'C', 39u to 'D',
        40u to 'E', 41u to 'F', 42u to 'G', 43u to 'H', 44u to 'I',
        45u to 'J', 46u to 'K', 47u to 'L', 48u to 'M', 49u to 'N',
        50u to 'O', 51u to 'P', 52u to 'Q', 53u to 'R', 54u to 'S',
        55u to 'T', 56u to 'U', 57u to 'V', 58u to 'W', 59u to 'X',
        60u to 'Y', 61u to 'Z'
    )

    fun str2Base62(str: String, salt: String = "nahida"): String {
        var tmp = str
        var code: String
        do { // hash碰撞后加一些字符串继续hash
            var hash = hashUtils.hash32x86(tmp.encodeToByteArray())
            val sb = StringBuilder()
            while (hash != 0u) {
                val remainder = hash % 62u
                hash /= 62u
                sb.append("${base62[remainder]}")
            }
            code = sb.reversed().toString()
            tmp += salt
        } while (LinkCache[code] != null)
        return code
    }
}
