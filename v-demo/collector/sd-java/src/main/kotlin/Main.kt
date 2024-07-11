package org.example

import com.sensorsdata.analytics.javasdk.SensorsAnalytics
import com.sensorsdata.analytics.javasdk.bean.EventRecord
import com.sensorsdata.analytics.javasdk.consumer.BatchConsumer


fun main() {
    val batchConsumer = BatchConsumer("http://127.0.0.1:8000/api/v1/data")
    val sa = SensorsAnalytics(batchConsumer)

    // 用户的 Distinct ID
    val distinctId = "nahida"

    // 记录用户登录事件
    val loginRecord = EventRecord.builder()
        .setDistinctId(distinctId)
        .isLoginId(true)
        .setEventName("UserLogin")
        .build()
    sa.track(loginRecord)
}