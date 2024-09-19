"""
用于Rocket.Chat删除历史消息(业务错误)
"""

import argparse
import json
import requests
from datetime import datetime

from tqdm import tqdm


bar_format = '{l_bar}{bar} [{n_fmt}/{total_fmt}] {percentage:3.0f}%'

def get_message_list():
    # RC 获取所有发送系统消息的roomId
    response = requests.request(
        method='GET',
        url=f'{args.rc_url}/api/v1/rooms.get',
        headers=headers,
    )
    body = response.json()
    rid_list = []
    for it in body['update']:
        if it['t'] == 'd': # 私聊
            rid_list.append(it['_id'])

    # RC 根据roomId获取这个聊天室的聊天记录
    # 只要清除这个时间节点之前系统通知的数据
    t = datetime.fromisoformat('2024-08-02T22:00:00.000Z'.replace("Z", "+00:00"))
    result_list = []
    print("正在获取消息数据...")
    for rid in tqdm(rid_list, bar_format=bar_format):
        offset = 0
        flag = True
        while(flag):
            response = requests.request(
                method='GET',
                url=f'{args.rc_url}/api/v1/im.history',
                headers=headers,
                params={'roomId': rid, 'offset': offset, 'count': 50},
            )
            body = response.json()
            for it in body['messages']:
                msg_ts = datetime.fromisoformat(it['ts'].replace("Z", "+00:00"))
                if msg_ts < t:
                    message = {
                        'roomId': it['rid'],
                        'msgId': it['_id'],
                        'ts': it['ts']
                    }
                    result_list.append(message)
            offset += 50
            flag = len(body['messages']) > 0

    return result_list


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--rc_url', type=str, required=False, default="http://192.168.0.130:3000")
    parser.add_argument('--rc_username', type=str, required=False, default="CKMRO.Bot")
    parser.add_argument('--rc_password', type=str, required=False, default="111111")
    args = parser.parse_args()

    # RC 管理员登入
    response = requests.request(
        method='POST',
        url=f'{args.rc_url}/api/v1/login',
        data={'user': args.rc_username, 'password': args.rc_password}
    )
    body = response.json()
    headers = {
        'X-User-Id': body["data"]["userId"],
        'X-Auth-Token': body["data"]["authToken"],
        'Content-Type': 'application/json'
    }

    message_list = get_message_list()

    # for it in message_list:
    #     print(it)
    print(f'待删除的消息数据有 {len(message_list)} 条')

    # 删除消息
    print("正在删除消息...")
    for it in tqdm(message_list, bar_format=bar_format):
        payload = json.dumps({
            "roomId": it['roomId'],
            "msgId": it['msgId'],
        })
        response = requests.request(
            method='POST',
            url=f'{args.rc_url}/api/v1/chat.delete',
            headers=headers,
            data=payload
        )
    
    message_list = get_message_list()
    print(f'待删除的消息数据有 {len(message_list)} 条')
