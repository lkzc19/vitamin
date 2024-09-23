"""
用于Rocket.Chat删除历史消息(业务错误)
"""

import argparse
from concurrent import futures
import json
import time
import requests
from datetime import datetime

from tqdm import tqdm
import threading
from concurrent.futures import ThreadPoolExecutor


bar_format = '{l_bar}{bar} [{n_fmt}/{total_fmt}] {percentage:3.0f}%'
t = datetime.fromisoformat('2024-08-02T22:00:00.000Z'.replace("Z", "+00:00"))


# RC 根据roomId获取这个聊天室的聊天记录
# 只要清除这个时间节点之前系统通知的数据
def get_and_delete(rid):
    offset = 0
    flag = True
    payload_list = []
    # 获取消息
    while(flag):
        response = requests.request(
            method='GET',
            url=f'{args.rc_url}/api/v1/im.history',
            headers=headers,
            params={'roomId': rid, 'latest': '2024-08-02T22:00:00.000Z', 'offset': offset, 'count': 50},
        )
        body = response.json()
        for it in body['messages']:
            msg_ts = datetime.fromisoformat(it['ts'].replace("Z", "+00:00"))
            if msg_ts < t:
                payload = json.dumps({
                    "roomId": it['rid'],
                    "msgId": it['_id'],
                })
                payload_list.append(payload)
        offset += 50
        flag = len(body['messages']) > 0
    # 删除消息
    for it in payload_list:
        try:
            response = requests.request(
                method='POST',
                url=f'{args.rc_url}/api/v1/chat.delete',
                headers=headers,
                data=it
            )
            name = threading.current_thread().name
            print(f"当前线程名称: {name} | {response.status_code}")
        except:
            pass


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

    print("删除消息中...")
    tasks, results = [], []
    with ThreadPoolExecutor(max_workers=3000) as executor:
        for rid in rid_list:
            tasks.append(executor.submit(get_and_delete, rid))
        for task in tqdm(futures.as_completed(tasks), total=len(tasks), bar_format=bar_format):
            results.append(task.result())
    
    print(f'OK')
