package consumer

import Constants
import com.sensorsdata.analytics.javasdk.SensorsAnalytics
import com.sensorsdata.analytics.javasdk.bean.EventRecord
import com.sensorsdata.analytics.javasdk.bean.FailedData
import com.sensorsdata.analytics.javasdk.consumer.FastBatchConsumer
import kotlin.test.Test


class FastBatchConsumerTests {

    @Test
    fun fastBatchConsumerTest() {
        // 发送失败的数据，收集容器。（仅测试使用，避免网络异常时，大批量数据发送失败同时保存内存中，导致 OOM ）
        val failedDataList = mutableListOf<FailedData>()

        // 参数含义：数据接收地址，是否定时上报，非定时上报阈值触发条数，内部缓存最大容量，定时刷新时间间隔，网络请求超时时间，请求失败之后异常数据回调
        // 提供多重构造函数，可以根据实际去调用不同的构造函数
        val fastBatchConsumer = FastBatchConsumer(Constants.serverUrl, false, 50, 6000, 2, 3) {
            failedDataList.add(it)
        }
        // 构建 sa 实例对象
        val sa = SensorsAnalytics(fastBatchConsumer)
        // 用户的 Distinct ID
        val distinctId = "foo9527"

        // 记录用户登录事件
        val loginRecord = EventRecord.builder()
            .setDistinctId(distinctId)
            .isLoginId(java.lang.Boolean.TRUE)
            .setEventName("UserLogin")
            .build()
        sa.track(loginRecord)
        sa.flush()

        // 异常数据的重发送设置(重发送尽量由另外一个单独的线程来处理完成，避免影响主线程处理逻辑)
        failedDataList.forEach {
            try {
                // 返回重发送接口发送成功与否，true:发送成功；false:发送失败
                println(fastBatchConsumer.resendFailedData(it))
            } catch (e: Exception) {
                // 处理重发送数据校验异常的情况
                e.printStackTrace()
            }
        }
    }
}