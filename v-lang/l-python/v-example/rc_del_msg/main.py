"""
用于Rocket.Chat删除历史消息(业务错误)
"""

import argparse
import requests


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--rc_url', type=str, required=False, default="http://192.168.0.130:4000")
    parser.add_argument('--rc_username', type=str, required=False, default="CKMRO.Bot")
    parser.add_argument('--rc_password', type=str, required=False, default="111111")
    args = parser.parse_args()

    response = requests.request(
        method="POST",
        url=f'{args.rc_url}/api/v1/login',
        data={"user": args.rc_username, "password": args.rc_password}
    )
    rcLoginResponse = response.json()
    headers = {
        'Authorization': f'Bearer {rcLoginResponse["data"]["authToken"]}',
        'X-User-Id': rcLoginResponse["data"]["userId"],
        'X-Auth-Token': rcLoginResponse["data"]["authToken"],
        'Content-Type': 'application/json'
    }
    print(headers)
