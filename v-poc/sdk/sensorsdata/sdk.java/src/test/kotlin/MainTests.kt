import com.sensorsdata.analytics.javasdk.SensorsAnalytics
import com.sensorsdata.analytics.javasdk.bean.IDMEventRecord
import com.sensorsdata.analytics.javasdk.consumer.BatchConsumer
import org.junit.jupiter.api.Test


class MainTests {

    @Test
    fun test() {
        val sa = SensorsAnalytics(BatchConsumer(Constants.serverUrl, 50, true, 0))
//        val sa = SensorsAnalytics(ConsoleConsumer(PrintWriter(System.out)))

        val record = IDMEventRecord.starter()
            .addIdentityProperty("cookieId", "nahida")
            .setEventName("FooBar")
            .addProperty("Keyword", "8844钛金手机")
            .build()
        sa.trackById(record)
    }
}