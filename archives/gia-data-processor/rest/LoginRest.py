import requests
import Cache
import config.RcConfig as RcConfig


def login(user=None, password=None):
    if user is None:
        user = RcConfig.get_bot_user()
    if password is None:
        password = RcConfig.get_bot_pwd()
    data = {
        "user": user,
        "password": password
    }
    response = requests.post(RcConfig.get_base_url() + '/api/v1/login', data=data)
    resp_data = response.json()

    Cache.headers = {
        'X-User-Id': resp_data["data"]["userId"],
        'X-Auth-Token': resp_data["data"]["authToken"]
    }
