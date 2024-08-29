
import argparse

import redis


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-e','--env', type=str, required=False, default="test")
    args = parser.parse_args()

    db = 3
    if args.env != "test":
        db = 6

    r = redis.Redis(host='192.168.0.130', port=6379, db=db)
    r.set('lkzc19', 'nahida')