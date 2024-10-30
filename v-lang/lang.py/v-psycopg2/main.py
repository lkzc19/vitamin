import psycopg2


if __name__ == '__main__':
    conn = psycopg2.connect(
        host='127.0.0.1',
        port=3432,
        dbname='vitamin',
        user='vitamin',
        password='vitamin',
    )

    c = conn.cursor()
    select_query = 'SELECT * FROM foo'
    c.execute(select_query)
    rows = c.fetchall()
    for row in rows:
        print(row)
