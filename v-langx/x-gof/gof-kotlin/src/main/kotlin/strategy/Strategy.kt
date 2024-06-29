package strategy

class Context(private val strategy: Strategy) {
    fun executeStrategy(userId: String, message: String) {
        strategy.execute(userId, message)
    }
}

interface Strategy {
    fun execute(userId: String, message: String)
}

class SmsStrategy : Strategy {
    override fun execute(userId: String, message: String) {
        println("[短信推送]: $userId, $message")
    }
}

class AppStrategy : Strategy {
    override fun execute(userId: String, message: String) {
        println("[App推送]: $userId, $message")
    }
}