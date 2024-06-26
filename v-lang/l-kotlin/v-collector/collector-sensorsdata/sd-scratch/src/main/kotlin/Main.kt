package org.example

import com.sensorsdata.analytics.javasdk.SensorsAnalytics
import com.sensorsdata.analytics.javasdk.bean.EventRecord
import com.sensorsdata.analytics.javasdk.consumer.BatchConsumer


fun main() {
    val batchConsumer = BatchConsumer("http://127.0.0.1:3333")
    val sa = SensorsAnalytics(batchConsumer)

    // 用户的 Distinct ID
    val distinctId = "ABCDEF123456789"

    // 记录用户登录事件
    val loginRecord = EventRecord.builder()
        .setDistinctId(distinctId)
        .isLoginId(true)
        .setEventName("UserLogin")
        .build()

    sa.track(loginRecord)
}