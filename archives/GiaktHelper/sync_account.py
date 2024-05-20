import argparse
import json

import psycopg2
import requests
from pymongo import MongoClient

import hashlib

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--rcBaseUrl', type=str, help='RC URL', required=True)
    parser.add_argument('--rcBotUsername', type=str, help='RC 机器人账号', required=True)
    parser.add_argument('--rcBotPassword', type=str, help='RC 机器人密码', required=True)
    parser.add_argument('--mongoUrl', type=str, help='Mongo URL', required=True)
    parser.add_argument('--pgDbName', type=str, help='pg 库名', required=True)
    parser.add_argument('--pgUser', type=str, help='pg user', required=True)
    parser.add_argument('--pgPassword', type=str, help='pg password', required=True)
    parser.add_argument('--pgHost', type=str, help='pg host', required=True)
    parser.add_argument('--pgPort', type=str, help='pg port', required=True)
    args = parser.parse_args()

    # 连接到 MongoDB 服务器
    client = MongoClient(args.mongoUrl)

    # 选择数据库
    db = client['mro']

    # accounts
    accounts_collection = db['mro_accounts']
    query = {'status': {'$in': [0, 1, 2, 3, 4]}}
    accounts_result = list(accounts_collection.find(query))

    # tmp = [document['userName'] for document in accounts_result]
    # distinct1 = set()
    # distinct2 = set()
    # for e in tmp:
    #     if e in distinct1:
    #         distinct2.add(e)
    #     distinct1.add(e)
    # if len(distinct2) > 0:
    #     print(distinct2)
    #     exit(-1)

    # profiles
    profiles_collection = db['mro_profiles']
    query = {}
    profiles_result = profiles_collection.find(query)
    profiles_map = {document['accountId']: document for document in profiles_result}

    # companies
    companies_collection = db['mro_companies']
    query = {}
    companies_result = companies_collection.find(query)
    companies_map = {document['_id']: document for document in companies_result}

    # customers
    customers_collection = db['mro_biz_customers']
    query = {'status': 0}
    customers_result = list(customers_collection.find(query))

    seller = {document['accountId']: document['ownerId'] for document in customers_result if document['catalog'] == 1}
    buyer = {document['accountId']: document['ownerId'] for document in customers_result if document['catalog'] == 2}

    print("==================== Mongo数据获取完成 =============================")

    response = requests.request(
        method="POST",
        url=f'{args.rcBaseUrl}/api/v1/login',
        data={"user": args.rcBotUsername, "password": args.rcBotPassword}
    )
    rcLoginResponse = response.json()
    headers = {
        'Authorization': f'Bearer {rcLoginResponse["data"]["authToken"]}',
        'X-User-Id': rcLoginResponse["data"]["userId"],
        'X-Auth-Token': rcLoginResponse["data"]["authToken"],
        'Content-Type': 'application/json'
    }

    print("==================== RC登录完成 =============================")

    rcUserList = []
    offset = 0
    while True:
        response = requests.request(
            method="GET",
            url=f'{args.rcBaseUrl}/api/v1/users.list',
            headers=headers,
            params={'count': 100, 'offset': offset}
        )
        rcUserListResponse = response.json()
        rcUserList.extend([obj['username'] for obj in rcUserListResponse['users']])

        if offset > rcUserListResponse['total']:
            break
        offset += 100

    print("==================== 获取RC用户完成 =============================")

    hash_object = hashlib.md5()
    create_error = list()
    create_error_account = list()
    # 遍历查询结果
    for document in accounts_result:
        accountId = str(document.get('_id'))
        username = str(document.get('userName'))
        nickname = profiles_map.get(accountId, {}).get('name')
        if nickname is None or str(nickname).strip() == '' or nickname == '-':
            nickname = username
        else:
            nickname = str(nickname).strip()

        if username in rcUserList:
            continue

        hash_object.update(username.encode('utf-8'))
        hash_object.hexdigest()

        response = requests.request(
            method="POST",
            url=f'{args.rcBaseUrl}/api/v1/users.create',
            headers=headers,
            data=json.dumps({
                "email": f'{username}@ckmro.com',
                "name": nickname,
                "password": hash_object.hexdigest(),
                "username": username
            })
        )
        if not response.ok:
            create_error.append(response.json())
            create_error_account.append(accountId)
            continue
        rcUserCreateResponse = response.json()

    # TODO 日志
    print("========================================================")
    for item in create_error:
        print(item)
    print("========================================================")
    for item in create_error_account:
        print(item)
    print("========================================================")

    rcUserMap = {}
    offset = 0
    while True:
        response = requests.request(
            method="GET",
            url=f'{args.rcBaseUrl}/api/v1/users.list',
            headers=headers,
            params={'count': 100, 'offset': offset}
        )
        rcUserListResponse = response.json()
        rcUserMap.update({obj['username']: obj['_id'] for obj in rcUserListResponse['users']})

        if offset > rcUserListResponse['total']:
            break
        offset += 100

    distinct_username = set()
    insert_data = list()
    for document in accounts_result:
        accountId = str(document.get('_id'))
        companyId = str(document.get('companyId'))
        username = str(document.get('userName'))
        nickname = profiles_map.get(accountId, {}).get('name')
        if nickname is None or str(nickname).strip() == '' or nickname == '-':
            nickname = username
        else:
            nickname = str(nickname).strip()

        if accountId in create_error_account:
            continue

        if username in distinct_username:
            continue
        else:
            distinct_username.add(username)

        insert_data.append((
            accountId,
            rcUserMap[username],
            username,
            profiles_map.get(accountId, {}).get('sex', 0),
            profiles_map.get(accountId, {}).get('email', ''),
            nickname,
            seller.get(accountId, ''),
            buyer.get(accountId, ''),
            companyId,
            companies_map.get(companyId, {}).get('name'),
            'PASSIVE'
        ))

    print("==================== APP用户客户构造完成 =============================")

    # 执行插入数据的 SQL 语句
    insert_query = """
        INSERT INTO rc_users (
            account_id,
            rc_user_id,
            user_name,
            gender,
            email,
            nick_name,
            seller_acct_id,
            buyer_acct_id,
            company_id,
            company_name,
            status
        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    """

    select_query = "SELECT account_id, rc_user_id, user_name FROM rc_users"

    conn = psycopg2.connect(
        dbname=args.pgDbName,
        user=args.pgUser,
        password=args.pgPassword,
        host=args.pgHost,
        port=args.pgPort
    )

    cur = conn.cursor()

    cur.execute(select_query)
    rows = cur.fetchall()

    register_aid = list()
    register_rid = list()
    register_uname = list()

    for row in rows:
        register_aid.append(row[0])
        register_rid.append(row[1])
        register_uname.append(row[2])

    print("==================== 插入APP用户完成 =============================")

    for item in insert_data:
        try:
            if (not item[0] in register_aid) and (not item[1] in register_rid) and (not item[2] in register_uname):
                cur = conn.cursor()
                cur.execute(insert_query, item)
                conn.commit()
                cur.close()
        except Exception as e:
            conn.reset()
            print(e)
    conn.close()
