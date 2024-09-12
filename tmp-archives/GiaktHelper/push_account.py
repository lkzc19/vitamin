# -*- coding: utf-8 -*-
# This file is auto-generated, don't edit it. Thanks.
import asyncio
import random
from datetime import datetime
import sys

from alibabacloud_push20160801 import models as push_20160801_models
from alibabacloud_push20160801.client import Client as Push20160801Client
from alibabacloud_tea_openapi import models as open_api_models
from alibabacloud_tea_util import models as util_models
from alibabacloud_tea_util.client import Client as UtilClient


class Sample:
    def __init__(self):
        pass

    @staticmethod
    def create_client():
        config = open_api_models.Config(
            # 必填，您的 AccessKey ID,
            access_key_id='xxx',
            # 必填，您的 AccessKey Secret,
            access_key_secret='xxx'
        )
        # Endpoint 请参考 https://api.aliyun.com/product/Push
        config.endpoint = f'cloudpush.aliyuncs.com'
        return Push20160801Client(config)

    @staticmethod
    async def main():
        current_time = datetime.now()
        body_list = ["豌豆射手", "双发豌豆射手", "三管豌豆射手", "机枪豌豆射手", "寒冰豌豆射手"]

        title = '植物大战僵尸'
        body = '账户[' + account + ']推送[' + str(current_time) + ']  ' + random.choice(body_list)

        push_request = push_20160801_models.PushRequest(
            app_key=xxx,
            push_type='NOTICE',
            device_type='ALL',
            target='ACCOUNT',
            target_value=account,
            store_offline=True,
            title=title,
            body=body,
            trim=True,
            android_target_user_type=1,
            android_popup_activity='com.ckmro.app.AwesomeApp.MainActivity',
            android_popup_title=title,
            android_popup_body=body,
            android_notification_channel="1",
            android_remind=True
        )
        try:
            # 复制代码运行请自行打印 API 的返回值
            await Sample.create_client().push_with_options_async(push_request, util_models.RuntimeOptions())
        except Exception as error:
            # 错误 message
            print(error.message)
            # 诊断地址
            print(error.data.get("Recommend"))
            UtilClient.assert_as_string(error.message)


if __name__ == '__main__':
    account = "17605943065"
    asyncio.run(Sample.main())
    print("OK")

