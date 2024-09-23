"""
用于Rocket.Chat删除历史消息(业务错误)
"""

import argparse
import json
import requests
from datetime import datetime

import threading
from concurrent.futures import ThreadPoolExecutor
import os
import multiprocessing


bar_format = '{l_bar}{bar} [{n_fmt}/{total_fmt}] {percentage:3.0f}%'
t = datetime.fromisoformat('2024-08-02T22:00:00.000Z'.replace("Z", "+00:00"))


def delete(payload):
    try:
        response = requests.request(
            method='POST',
            url=f'{args.rc_url}/api/v1/chat.delete',
            headers=headers,
            data=payload
        )
        current_process_name = multiprocessing.current_process().name
        current_thread_name = threading.current_thread().name
        print(f"{current_process_name} | {current_thread_name} | {response.status_code}")
    except:
        pass


# RC 根据roomId获取这个聊天室的聊天记录
# 只要清除这个时间节点之前系统通知的数据
def get_and_delete(rid, headers, args):
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
    with ThreadPoolExecutor(max_workers=40) as executor:
        for it in payload_list:
            executor.submit(delete, it)


if __name__ == '__main__':
    multiprocessing.freeze_support()

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
    cores = int(os.cpu_count())
    process_pool = multiprocessing.Pool(processes=cores)
    for it in rid_list:
        process_pool.apply(get_and_delete, (it, headers, args))
    process_pool.close()
    process_pool.join()
    
    print(f'OK')
