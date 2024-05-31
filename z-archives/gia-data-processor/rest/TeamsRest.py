import requests
import config.RcConfig as RcConfig
import Cache


def members(team_id: str):
    params = {
        'teamId': team_id
    }
    response = requests.get(RcConfig.get_base_url() + '/api/v1/teams.members', headers=Cache.headers, params=params)
    resp_data = response.json()
    user_list = []
    for item in resp_data['members']:
        user = {
            'id': item['user']['_id'],
            'username': item['user']['username'],
        }
        user_list.append(user)
    return user_list
