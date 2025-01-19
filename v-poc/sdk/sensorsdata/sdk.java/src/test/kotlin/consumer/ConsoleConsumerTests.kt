package consumer

import com.sensorsdata.analytics.javasdk.SensorsAnalytics
import com.sensorsdata.analytics.javasdk.bean.EventRecord
import com.sensorsdata.analytics.javasdk.bean.UserRecord
import com.sensorsdata.analytics.javasdk.consumer.ConsoleConsumer
import java.io.PrintWriter
import kotlin.test.Test


class ConsoleConsumerTests {

    @Test
    fun consoleConsumerTest() {
        // 将数据输出到标准输出
        val writer = PrintWriter(System.out);

        // 使用 ConsoleConsumer 初始化 SensorsAnalytics
        val sa = SensorsAnalytics(ConsoleConsumer(writer))

        // 使用神策分析记录用户行为数据
        // 用户的 Distinct ID
        val distinctId = "foo9527"

        // 记录用户登录事件
        val loginRecord = EventRecord.builder()
            .setDistinctId(distinctId)
            .isLoginId(java.lang.Boolean.TRUE)
            .setEventName("UserLogin")
            .build()
        sa.track(loginRecord)

        var userRecord = UserRecord.builder().setDistinctId(distinctId).isLoginId(java.lang.Boolean.TRUE)
            .addProperty("Sex", "Male") // 设置用户性别属性（Sex）为男性
            .build()
        sa.profileSet(userRecord!!)

        userRecord = UserRecord.builder().setDistinctId(distinctId).isLoginId(java.lang.Boolean.TRUE)
            .addProperty("UserLv", "Elite VIP") // 设置用户等级属性（Level）为 VIP
            .build()
        sa.profileSet(userRecord)

        // Flush the writer
        writer.flush()
    }
}