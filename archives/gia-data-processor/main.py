import sys
import time

import util.ArgPaserUtils as ArgPaserUtils
import util.EnvUtils as EnvUtils
import Cache
from enums.OpEnum import OpEnum

import rest.LoginRest as LoginRest
import service.UsersService as UsersService


def init():
    ArgPaserUtils.arg_parse()
    EnvUtils.load_env(Cache.arg['env'])


if __name__ == '__main__':
    init()

    if Cache.arg['op'] == OpEnum.CLEAR_USER.value:
        if Cache.arg['token'] != 'zxczxc':
            print('token错误 取消操作')
            sys.exit(1)
        print('10秒后执行...')
        # 机器人登录
        LoginRest.login()
        # time.sleep(10)
        UsersService.clear_rc()
    elif Cache.arg['op'] == OpEnum.USER_INFO.value:
        UsersService.find_user_info(Cache.arg['aid'], Cache.arg['rid'], Cache.arg['uname'])
    # elif 验证码
