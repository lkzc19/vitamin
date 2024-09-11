import argparse

import psycopg2
from psycopg2.errors import UniqueViolation


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--pg_host', type=str, required=False, default="192.168.0.132")
    parser.add_argument('--pg_port', type=str, required=False, default="5432")
    parser.add_argument('--pg_dbname', type=str, required=False, default="giakt_test")
    parser.add_argument('--pg_user', type=str, required=False, default="giakt_test")
    parser.add_argument('--pg_password', type=str, required=False, default="giakt_test")
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

    platform_list = []
    for row in rows: 
        if row[5] != 'CUSTOMER':
            platform_list.append(row[4])

    contact_list = []
    for row in rows:
        if row[5] == 'CUSTOMER' and row[3] not in platform_list:
            contact_list.append({'id': row[0], 'ownerId': row[3], 'targetId': row[4], 'role': 'CUSTOMER'})

    for it in contact_list:
        try:
            sql = 'INSERT INTO rc_contacts_v2(owner_id, target_id, role) VALUES (%s, %s, %s)'
            cur.execute(sql, (it['ownerId'], it['targetId'], it['role']))
            conn.commit()
        except UniqueViolation:
            pass

    for it in contact_list:
        sql = 'SELECT t.name FROM rc_tags t INNER JOIN rc_contact_tags ct ON t.id = ct.tag_id WHERE ct.contact_id = %s'
        cur.execute(sql, (it['id'],))
        tmp_list = cur.fetchall()
        if len(tmp_list) == 0:
            continue
        tag_name_list = [item[0] for item in tmp_list]
        try:
            sql = 'INSERT INTO rc_contacts_tag_v2(owner_id, target_id, tags) VALUES (%s, %s, %s)'
            cur.execute(sql, (it['ownerId'], it['targetId'], tag_name_list))
            conn.commit()
        except UniqueViolation:
            pass