import requests
import json


rcBaseUrl = "http://192.168.0.40:3000"
white_list = ['CKMRO.Bot', 'rocket.cat']


if __name__ == '__main__':
    response = requests.request(
        method="POST",
        url=f'{rcBaseUrl}/api/v1/login',
        data={"user": "CKMRO.Bot", "password": "111111"}
    )
    rcLoginResponse = response.json()
    headers = {
        'Authorization': f'Bearer {rcLoginResponse["data"]["authToken"]}',
        'X-User-Id': rcLoginResponse["data"]["userId"],
        'X-Auth-Token': rcLoginResponse["data"]["authToken"],
        'Content-Type': 'application/json'
    }

    rcUserList = []
    offset = 0
    while True:
        response = requests.request(
            method="GET",
            url=f'{rcBaseUrl}/api/v1/users.list',
            headers=headers,
            params={'count': 100, 'offset': offset}
        )
        rcUserListResponse = response.json()
        rcUserList.extend([obj['username'] for obj in rcUserListResponse['users']])

        if offset > rcUserListResponse['total']:
            break
        offset += 100

    for username in rcUserList:
        if username in white_list:
            continue
        response = requests.request(
            method="POST",
            url=f'{rcBaseUrl}/api/v1/users.delete',
            headers=headers,
            data=json.dumps({
                'username': username
            })
        )
        print(response.text)
