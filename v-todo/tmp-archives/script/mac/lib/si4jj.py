# -*- coding: UTF-8 -*-

import argparse
import os
import requests
import smtplib
from email.mime.text import MIMEText
from datetime import datetime

config = {
    'baseUrl': 'https://api.juejin.cn',
    'apiUrl': {
        'getTodayStatus': '/growth_api/v1/get_today_status',
        'checkIn': '/growth_api/v1/check_in',
        'getLotteryConfig': '/growth_api/v1/lottery_config/get',
        'drawLottery': '/growth_api/v1/lottery/draw'
    },
    'email': {
        'host': 'smtp.sina.com',
        'port': 465,
        'sender': 'steart@sina.com',
        'pwd': os.environ.get('ZENV_STEART_SINA_PASS')
    }
}


def init():
    parser = argparse.ArgumentParser()
    parser.add_argument('--cookie', type=str, help='浏览器中获取cookie')
    parser.add_argument('--receiver', type=str, help='通知结果的邮箱')
    parser.add_argument('--debug', default=False, type=bool, help='测试')
    args = parser.parse_args()

    headers = {
        'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36',
        'cookie': args.cookie
    }

    config['headers'] = headers
    config['email']['receiver'] = args.receiver

    config['debug'] = args.debug

    test()


# 测试
def test():
    if not config['debug']:
        return
    print('===测试开始===')
    send_email()
    print('===测试结束===')


# 查询今日是否已经签到
def get_today_check_status():
    response = requests.get(config['baseUrl'] + config['apiUrl']['getTodayStatus'], headers=config['headers'])
    print('===查询今日是否已经签到: \n' + str(response.json()))
    if response.json()['err_no'] != 0:
        send_email('今日掘金签到查询：失败', response.json())
    return {'isError': response.json()['err_no'] != 0, 'isCheck': response.json()['data']}


# 签到
def check_in():
    result = get_today_check_status()
    # 如果查询失败，或者已签到，则直接返回
    if result['isError'] or result['isCheck']:
        return
    response = requests.post(config['baseUrl'] + config['apiUrl']['checkIn'], headers=config['headers'])
    print('===签到: \n' + str(response.json()))
    if response.json()['err_no'] != 0:
        send_email('今日掘金签到：失败', response.json())
    # else:
    #     content = '签到积分：' + str(response.json()['data']['incr_point']) + '\n' \
    #             + '全部积分：' + str(response.json()['data']['sum_point'])
    #     send_email('今日掘金签到：成功', content)


# 获得今日是否有一次免费抽奖
def get_today_draw_status():
    response = requests.get(config['baseUrl'] + config['apiUrl']['getLotteryConfig'], headers=config['headers'])
    print('===获得今日是否有一次免费抽奖: \n' + str(response.json()))
    return {'isError': response.json()['err_no'] != 0, 'canDraw': response.json()['data']['free_count'] == 1}


# 抽奖
def draw():
    result = get_today_draw_status()
    if result['isError'] or not result['canDraw']:
        return
    response = requests.post(config['baseUrl'] + config['apiUrl']['drawLottery'], headers=config['headers'])
    print('===抽奖: \n' + str(response.json()))
    # 抽奖成功
    if response.json()['err_no'] == 0:
        # 抽到实物通知
        if response.json()['data']['lottery_type'] > 1:
            send_email('卧槽，居然抽到实物了！', response.json()['data']['lottery_name'])
        # 幸运值 6000 必抽中实物
        if response.json()['data']['total_lucky_value'] >= 5990:
            send_email('幸运值即将溢出！！！高能时刻！！！', '幸运值：' + response.json()['data']['total_lucky_value'])


# 发送邮件
def send_email(subject='掘金脚本执行结果通知', content='测试'):
    print('===' + str(datetime.now()))
    msg = MIMEText(content)
    msg['Subject'] = subject
    msg['From'] = config['email']['sender']
    msg['To'] = config['email']['receiver']

    server = smtplib.SMTP_SSL(host=config['email']['host'], port=config['email']['port'])
    server.login(config['email']['sender'], config['email']['pwd'])
    server.sendmail(config['email']['sender'], config['email']['receiver'], msg.as_string())
    server.quit()
    print('===' + str(datetime.now()))


if __name__ == '__main__':
    print('=====' + str(datetime.now()) + '=====')
    init()
    check_in()
    draw()
    print('\n\n')
