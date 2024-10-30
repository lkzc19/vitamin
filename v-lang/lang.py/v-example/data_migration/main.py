import argparse

import psycopg2
from psycopg2.errors import UniqueViolation
from tqdm import tqdm


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--pg_host', type=str, required=False, default="192.168.0.132")
    parser.add_argument('--pg_port', type=str, required=False, default="5432")
    parser.add_argument('--pg_dbname', type=str, required=False, default="giakt")
    parser.add_argument('--pg_user', type=str, required=False, default="giakt")
    parser.add_argument('--pg_password', type=str, required=False, default="giakt")
    args = parser.parse_args()

    conn = psycopg2.connect(
        host=args.pg_host,
        port=args.pg_port,
        dbname=args.pg_dbname,
        user=args.pg_user,
        password=args.pg_password,
    )
    cur = conn.cursor()

    select = 'SELECT * FROM rc_contacts'
    cur.execute(select)
    rows = cur.fetchall()
            
    # 找到所有客户与客户之间联系人关系
    platform_list = [] # 平台人员id
    for row in rows: 
        if row[5] != 'CUSTOMER':
            platform_list.append(row[4])
    customer_list = [] # 客户与客户之间联系人关系
    for row in rows:
        if row[5] == 'CUSTOMER' and row[3] not in platform_list:
            customer_list.append({'id': row[0], 'ownerId': row[3], 'targetId': row[4], 'role': 'CUSTOMER'})

    # 联系人迁移
    print('联系人迁移')
    bar_format = '{l_bar}{bar} [{n_fmt}/{total_fmt}] {percentage:3.0f}%'
    for it in tqdm(customer_list, bar_format=bar_format):
        sql = 'INSERT INTO rc_contacts_v2(owner_id, target_id, role) VALUES (%s, %s, %s)'
        try:
            cur.execute(sql, (it['ownerId'], it['targetId'], it['role']))
            conn.commit()
        except UniqueViolation:
            conn.rollback()
    
    # 联系人标签迁移
    print('联系人标签迁移')
    for it in tqdm(rows, bar_format=bar_format):
        sql = 'SELECT t.name FROM rc_tags t INNER JOIN rc_contact_tags ct ON t.id = ct.tag_id WHERE ct.contact_id = %s'
        cur.execute(sql, (it[0],))
        tmp_list = cur.fetchall()
        if len(tmp_list) == 0:
            continue
        tag_name_list = [item[0] for item in tmp_list]
        sql = 'INSERT INTO rc_contacts_tag_v2(owner_id, target_id, tags) VALUES (%s, %s, %s)'
        try:
            cur.execute(sql, (it[3], it[4], tag_name_list))
            conn.commit()
        except UniqueViolation:
            conn.rollback()
