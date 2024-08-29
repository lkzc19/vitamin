
import argparse

import redis


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-e','--env', type=str, required=False, default="test")
    parser.add_argument('-k','--key', type=str, required=True)
    args = parser.parse_args()

    db = 3
    if args.env != "test":
        db = 6

    r = redis.Redis(host='192.168.0.130', port=6379, db=db)

    key = f'vcode:{args.key}'
    value = r.get(key)
    if value == None:
        print(f'{args.key} 不存在')
    else:
        print(value.decode('utf-8'))
