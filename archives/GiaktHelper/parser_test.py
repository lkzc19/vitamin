import argparse
import hashlib

if __name__ == '__main__':
    # parser = argparse.ArgumentParser()
    # parser.add_argument('--rcBaseUrl', type=str, help='RC URL', required=True)
    # parser.add_argument('--rcBotUsername', type=str, help='RC 机器人账号', required=True)
    # parser.add_argument('--rcBotPassword', type=str, help='RC 机器人密码', required=True)
    # parser.add_argument('--mongoUrl', type=str, help='Mongo URL', required=True)
    # parser.add_argument('--pgDbName', type=str, help='pg 库名', required=True)
    # parser.add_argument('--pgUser', type=str, help='pg user', required=True)
    # parser.add_argument('--pgPassword', type=str, help='pg password', required=True)
    # parser.add_argument('--pgHost', type=str, help='pg host', required=True)
    # parser.add_argument('--pgPort', type=str, help='pg port', required=True)
    # args = parser.parse_args()
    # rcBaseUrl = args.rcBaseUrl
    # mongoUrl = args.mongoUrl
    #
    # print(rcBaseUrl)
    hash_object = hashlib.md5()
    hash_object.update("15060007175".encode('utf-8'))
    md5_hash = hash_object.hexdigest()
    print("MD5 哈希值:", md5_hash)

