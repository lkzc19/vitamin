import config.RcConfig as RcConfig
import rest.UsersRest as UsersRest
import repo.UsersRepo as UsersRepo


def users_list_all():
    ret = []
    count = 100
    offset = 0
    result = UsersRest.list_rest(count, offset)
    ret.extend(result['user_list'])
    total = result['total']
    while total > offset:
        count += 100
        offset += result['count']
        tmp = UsersRest.list_rest(count, offset)
        ret.extend(tmp['user_list'])
        total = tmp['total']
    return ret


# 获取非开发人员rc账号
def get_need_delete_username():
    need_delete_username = []
    # 获取需要删除的RC账户
    for user in users_list_all():
        # 白名单中的不要删除
        if user['username'] not in RcConfig.get_white():
            need_delete_username.append(user['username'])
    return need_delete_username


def clear_rc_user():
    # 调用RC删除接口
    for username in get_need_delete_username():
        try:
            UsersRest.delete_rest(username)
        except:
            print(username)


def clear_rc():
    print("清理用户 开始...")
    clear_rc_user()
    print("清理用户 结束...")


def find_user_info(aid=None, rid=None, uname=None):
    user = UsersRepo.find_info(aid, rid, uname)
    if user is None:
        return
    print('用户信息:')
    print('acctId: \t\t' + user[3])
    print('rcUserId: \t\t' + user[4])
    print('userName: \t\t' + user[5])

    seller = UsersRepo.find_info(aid=user[10])
    if seller is None:
        return
    print('用户 的 业务员:')
    print('acctId: \t\t' + seller[3])
    print('rcUserId: \t\t' + seller[4])
    print('userName: \t\t' + seller[5])

    buyer = UsersRepo.find_info(aid=user[11])
    if buyer is None:
        return
    print('用户 的 采购员:')
    print('acctId: \t\t' + buyer[3])
    print('rcUserId: \t\t' + buyer[4])
    print('userName: \t\t' + buyer[5])
