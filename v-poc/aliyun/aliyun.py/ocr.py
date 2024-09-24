# -*- coding: utf-8 -*-

import json
import base64
import os
import ssl

try:
    from urllib.error import HTTPError
    from urllib.request import Request, urlopen
except ImportError:
    from urllib2 import Request, urlopen, HTTPError

context = ssl._create_unverified_context()


def get_img(img_file):
    """将本地图片转成base64编码的字符串，或者直接返回图片链接"""
    # 简单判断是否为图片链接
    if img_file.startswith("http"):
        return img_file
    else:
        with open(os.path.expanduser(img_file), 'rb') as f:  # 以二进制读取本地图片
            data = f.read()
    try:
        encodestr = str(base64.b64encode(data), 'utf-8')
    except TypeError:
        encodestr = base64.b64encode(data)

    return encodestr


def posturl(headers, body):
    """发送请求，获取识别结果"""
    try:
        params = json.dumps(body).encode(encoding='UTF8')
        req = Request(REQUEST_URL, params, headers)
        r = urlopen(req, context=context)
        html = r.read()
        return html.decode("utf8")
    except HTTPError as e:
        print(e.code)
        print(e.read().decode("utf8"))


def request(appcode, img_file, params):
    # 请求参数
    if params is None:
        params = {}
    img = get_img(img_file)
    params.update({'image': img})

    # 请求头
    headers = {
        'Authorization': 'APPCODE %s' % appcode,
        'Content-Type': 'application/json; charset=UTF-8'
    }

    response = posturl(headers, params)
    print(response)


# 请求接口
REQUEST_URL = "https://tysbgpu.market.alicloudapi.com/api/predict/ocr_general"

if __name__ == "__main__":
    # 配置信息
    appcode = "你的APPCODE"
    img_file = "图片链接/本地图片路径"
    params = {
        "configure": {
            "min_size": 16,  # 图片中文字的最小高度，单位像素（此参数目前已经废弃）
            "output_prob": True,  # 是否输出文字框的概率
            "output_keypoints": False,  # 是否输出文字框角点
            "skip_detection": False,  # 是否跳过文字检测步骤直接进行文字识别
            "without_predicting_direction": False,  # 是否关闭文字行方向预测
        }
    }

    request(appcode, img_file, params)
