import sys

from util.PgUtils import PgUtils


def list_all():
    with PgUtils() as utils:
        sql = 'SELECT * FROM rc_users'
        result = utils.execute_query(sql)
        for row in result:
            print(row)


def find_info(aid=None, rid=None, uname=None):
    sql: str
    param: str

    if aid is not None:
        sql = 'SELECT * FROM rc_users where account_id = %s'
        param = aid
    elif rid is not None:
        sql = 'SELECT * FROM rc_users where rc_user_id = %s'
        param = rid
    elif uname is not None:
        sql = 'SELECT * FROM rc_users where user_name = %s'
        param = uname
    else:
        print('缺少 aid rid uname 其中之一')
        sys.exit(1)

    with PgUtils() as utils:
        result = utils.execute_query(sql, (param,))
        if len(result) > 0:
            return result[0]
        else:
            return None
