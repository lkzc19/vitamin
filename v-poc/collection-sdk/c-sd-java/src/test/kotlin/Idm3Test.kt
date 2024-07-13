import com.sensorsdata.analytics.javasdk.SensorsAnalytics
import com.sensorsdata.analytics.javasdk.bean.*
import com.sensorsdata.analytics.javasdk.consumer.BatchConsumer
import java.util.*
import kotlin.test.Test


class Idm3Test {

    @Test
    fun idm3Test() {
        val batchConsumer = BatchConsumer("http://127.0.0.1:8000/api/v1/data")
        val sa = SensorsAnalytics(batchConsumer)

        //设置公共属性,以后上传的每一个事件都附带该属性
        val propertiesRecord = SuperPropertiesRecord.builder()
            .addProperty("\$os", "Windows")
            .addProperty("\$os_version", "8.1")
            .addProperty("\$ip", "123.123.123.123")
            .build()
        sa.registerSuperProperties(propertiesRecord);

        // 1. 全域用户关联已模糊匿名概念，如果拿到前端匿名 id，例如 cookieId，可直接设置触发事件
        val cookieId = "cookieId_nahida"

        // 1.1 访问首页
        // 前面有$开头的property字段，是SA提供给用户的预置字段
        // 对于预置字段，已经确定好了字段类型和字段的显示名
        val eventRecord = IDMEventRecord.starter()
            .addIdentityProperty("cookieId", cookieId)
            .setEventName("track")
            // '$time' 属性是系统预置属性，表示事件发生的时间，如果不填入该属性，则默认使用系统当前时间
            .addProperty("\$time", Calendar.getInstance().time)
            .addProperty("Channel", "vitamin")
            .addProperty("\$project", "sd-scratch")
            .addProperty("\$token", "token_nahida")
            .build()
        sa.trackById(eventRecord)
        // 1.2 搜索商品
        val searchRecord = IDMEventRecord.starter()
            .addIdentityProperty("cookieId", cookieId)
            .setEventName("SearchProduct")
            .addProperty("Keyword", "8844钛金手机")
            .build()
        sa.trackById(searchRecord)
        // 1.3 浏览商品
        val lookRecord = IDMEventRecord.starter()
            .addIdentityProperty("cookieId", cookieId)
            .setEventName("ViewProduct")
            .addProperty("ProductName", "8844钛金手机")
            .addProperty("ProductType", "智能手机")
            .addProperty("ShopName", "8844官方旗舰店")
            .build()
        sa.trackById(lookRecord)

        // 2.2 用户注册时，填充了一些个人信息，可以用Profile接口记录下来
        val interests = listOf("movie", "swim")

        val registerId = "registerId_nahida"

        var userRecord = IDMUserRecord.starter()
            .addIdentityProperty(SensorsAnalyticsIdentity.LOGIN_ID, registerId)
            .addProperty("\$city", "武汉")
            .addProperty("\$province", "湖北")
            .addProperty("\$name", "纳西妲")
            .addProperty("\$signup_time", Calendar.getInstance().time)
            .addProperty("Gender", "male")
            .addProperty("Age", 20)
            .addProperty("Interest", interests)
            .build()
        sa.profileSetById(userRecord)
        //2.3 设置首次访问时间
        userRecord = IDMUserRecord.starter()
            .addIdentityProperty(SensorsAnalyticsIdentity.LOGIN_ID, registerId)
            .addProperty("\$first_visit_time", Calendar.getInstance().time)
            .build()
        sa.profileSetOnceById(userRecord)
        //2.4 追加属性
        val newInterest = listOf("ball")
        val appendRecord = IDMUserRecord.starter()
            .addIdentityProperty(SensorsAnalyticsIdentity.LOGIN_ID, registerId)
            .addProperty("Interest", newInterest)
            .build()
        sa.profileAppendById(appendRecord);
        //2.5 给属性加值
        val incrementRecord = IDMUserRecord.starter()
            .addIdentityProperty(SensorsAnalyticsIdentity.LOGIN_ID, registerId)
            .addProperty("Age", 1)
            .build()
        sa.profileIncrementById(incrementRecord);
        //2.6 移除用户属性
        val unsetRecord = IDMUserRecord.starter()
            .addIdentityProperty(SensorsAnalyticsIdentity.LOGIN_ID, registerId)
            .addProperty("Age", 1)
            .build()
        sa.profileUnsetById(unsetRecord)
        // 3. 用户注册后，进行后续行为
        // 3.1 提交订单和提交订单详情
        // 订单的信息
        val orderRecord = IDMEventRecord.starter()
            .addIdentityProperty(SensorsAnalyticsIdentity.LOGIN_ID, registerId)
            .setEventName("SubmitOrder")
            .addProperty("OrderId", "SN_123_AB_TEST")
            .build()
        sa.trackById(orderRecord)
        // 3.2 支付订单和支付订单详情
        // 整个订单的支付情况
        val payRecord = IDMEventRecord.starter()
            .addIdentityProperty(SensorsAnalyticsIdentity.LOGIN_ID, registerId)
            .setEventName("PayOrder")
            .addProperty("PaymentMethod", "AliPay")
            .addProperty("AllowanceAmount", 30.0)
            .addProperty("PaymentAmount", 1204.0)
            .build()
        sa.trackById(payRecord)
        //物品纬度表上报
        val itemId = "product001"
        val itemType = "mobile"
        val addRecord = ItemRecord.builder()
            .setItemId(itemId)
            .setItemType(itemType)
            .addProperty("Color", "white")
            .build()
        sa.itemSet(addRecord)
        //删除物品纬度信息
        val deleteRecord = ItemRecord.builder()
            .setItemId(itemId)
            .setItemType(itemType)
            .build()
        sa.itemDelete(deleteRecord)
    }
}