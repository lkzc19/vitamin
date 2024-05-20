import argparse

from enums.EnvEnum import EnvEnum
from enums.OpEnum import OpEnum
import Cache

arg = {}


def arg_parse():
    parser = argparse.ArgumentParser(description='这是一个示例')
    # 添加命令行参数
    # parser.add_argument('input', help='输入文件')
    parser.add_argument('--env', default=EnvEnum.LOCAL.value, choices=EnvEnum.value_list(), help='环境')
    parser.add_argument('op', choices=OpEnum.value_list(), help='操作')
    parser.add_argument('--token', help='清理用户口令，避免误删')
    parser.add_argument('--rid', help='如果 op=user_info 可以输入 rcUserId 来查找该用户数据')
    parser.add_argument('--aid', help='如果 op=user_info 可以输入 AccountId 来查找该用户数据')
    parser.add_argument('--uname', help='如果 op=user_info 可以输入 username 来查找该用户数据')
    # 解析命令行参数
    args = parser.parse_args()

    Cache.arg['env'] = args.env
    Cache.arg['op'] = args.op
    Cache.arg['token'] = args.token
    Cache.arg['rid'] = args.rid
    Cache.arg['aid'] = args.aid
    Cache.arg['uname'] = args.uname
