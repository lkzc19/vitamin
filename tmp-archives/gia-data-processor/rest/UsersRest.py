import requests
import config.RcConfig as RcConfig
import Cache


def list_rest(count: int, offset: int):
    params = {
        'count': count,
        'offset': offset
    }
    response = requests.get(RcConfig.get_base_url() + '/api/v1/users.list', headers=Cache.headers, params=params)
    resp_data = response.json()
    user_list = []
    for item in resp_data['users']:
        user = {
            'id': item['_id'],
            'username': item['username'],
        }
        user_list.append(user)
    return {
        'user_list': user_list,
        'count': resp_data['count'],
        'offset': resp_data['offset'],
        'total': resp_data['total']
    }


def delete_rest(username):
    data = {
        'username': username
    }
    response = requests.post(RcConfig.get_base_url() + '/api/v1/users.delete', headers=Cache.headers, data=data)
    # resp_data = response.json()
    if response.status_code != 200:
        print(response.json())

