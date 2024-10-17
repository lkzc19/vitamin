package consumer

import com.sensorsdata.analytics.javasdk.ISensorsAnalytics
import com.sensorsdata.analytics.javasdk.SensorsAnalytics
import com.sensorsdata.analytics.javasdk.bean.EventRecord
import com.sensorsdata.analytics.javasdk.consumer.DebugConsumer
import kotlin.test.Test


class DebugConsumerTests {

    @Test
    fun debugConsumerTest() {
        val sa: ISensorsAnalytics = SensorsAnalytics(DebugConsumer(Constants.serverUrl, true))

        // 用户的 Distinct ID
        val distinctId = "foo9527"

        // 记录用户登录事件
        val loginRecord = EventRecord.builder()
            .setDistinctId(distinctId)
            .isLoginId(java.lang.Boolean.TRUE)
            .setEventName("UserLogin")
            .build()
        sa.track(loginRecord)
    }
}