import argparse
import os
import time

import psycopg2

import oss2
from oss2.credentials import EnvironmentVariableCredentialsProvider
from tqdm import tqdm


os.environ['OSS_ACCESS_KEY_ID'] = 'XXX'
os.environ['OSS_ACCESS_KEY_SECRET'] = 'XXX'
auth = oss2.ProviderAuth(EnvironmentVariableCredentialsProvider())
bucket = oss2.Bucket(auth, 'https://oss-cn-shanghai.aliyuncs.com', 'ckmro')


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--pg_host', type=str, required=False, default="192.168.0.132")
    parser.add_argument('--pg_port', type=str, required=False, default="5432")
    parser.add_argument('--pg_dbname', type=str, required=False, default="giakt")
    parser.add_argument('--pg_user', type=str, required=False, default="giakt")
    parser.add_argument('--pg_password', type=str, required=False, default="giakt")
    args = parser.parse_args()

    conn = psycopg2.connect(
        host=args.pg_host,
        port=args.pg_port,
        dbname=args.pg_dbname,
        user=args.pg_user,
        password=args.pg_password,
    )
    cur = conn.cursor()

    select = 'SELECT * FROM rc_users'
    cur.execute(select)
    rows = cur.fetchall()

    bar_format = '{l_bar}{bar} [{n_fmt}/{total_fmt}] {percentage:3.0f}%'
    for it in tqdm(rows, bar_format=bar_format):
        rcUserId = it[4]
        # key = f'dev/ckmro/apps/avatar/{rcUserId}'
        key = f'ckmro/apps/avatar/{rcUserId}'
        if not bucket.object_exists(key):
            filename = ''
            if it[10] == '': # 判断是 平台 or 客户
                filename = './default_service.png'
            else:
                filename = './default_customer.png'
            bucket.put_object_from_file(key, filename)
