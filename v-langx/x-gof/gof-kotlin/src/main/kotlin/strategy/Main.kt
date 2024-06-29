package strategy

import kotlin.random.Random

fun main() {
    val action = Action.random()

    val context = if (action == Action.SMS) {
        Context(SmsStrategy())
    } else if (action == Action.APP) {
        Context(AppStrategy())
    } else {
        throw IllegalStateException("Unexpected action: $action")
    }

    context.executeStrategy("nahida", "这是一个冷笑话")
}

enum class Action {
    SMS, APP;

    companion object {
        fun random(): Action {
            val values = entries.toTypedArray() // 获取所有枚举值
            return values[Random.nextInt(values.size)] // 随机选择一个枚举值
        }
    }
}