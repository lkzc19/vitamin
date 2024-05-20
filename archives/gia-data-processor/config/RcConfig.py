
config = {}
root = 'rc'


def get_base_url():
    return config['baseUrl']


def get_bot_user():
    return config['bot']['user']


def get_bot_pwd():
    return config['bot']['password']


def get_white():
    return config['white']
