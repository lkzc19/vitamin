# -*- coding: UTF-8 -*-

import argparse
import base64
import sys
from datetime import datetime
import os
import requests


if __name__ == '__main__':
    # 入参
    parser = argparse.ArgumentParser(description='github图床工具')
    parser.add_argument('img', help='图片地址')
    args = parser.parse_args()

    # 读取文件
    with open(args.img, 'rb') as it:
        file = it.read()

    # 判断是否是图片
    _, extension = os.path.splitext(args.img)
    image_extensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp']
    if extension.lower() not in image_extensions:
        print('[' + extension + ']文件非图片，不能上传')
        sys.exit(1)

    # 上传到gitee上的文件名 上传的频率不是很高, 使用秒即可
    filename = datetime.now().strftime("%Y-%m-%d-%H-%M-%S") + extension

    path_params = {
        'owner': 'lkzc19',
        'repo': 'blasphemy.zimg',
        'path': 'drinkice/' + filename
    }
    headers = {
        "Authorization": "Bearer {}".format(os.environ.get('ZENV_GITHUB_BLASPHEMY')),
        'Content-Type': 'application/octet-stream'
    }
    body = {
        'content': base64.b64encode(file).decode('utf-8'), # 将图片文件转换为 Base64 编码
        'message': 'feat: 上传图片'
    }
    # https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28#create-or-update-file-contents
    response = requests.put(
        'https://api.github.com/repos/{owner}/{repo}/contents/{path}'.format_map(path_params),
        headers=headers,
        json=body
    )
    if not response.ok:
        print('上传失败: ' + response.json()['message'])
        sys.exit(1)

    # 成功输出图片地址
    print('上传成功')
    gitee_img_url = response.json()['content']['download_url']
    print('markdown: \t' + ('![]({})'.format(gitee_img_url)))
    print('URL: \t\t' + gitee_img_url)
