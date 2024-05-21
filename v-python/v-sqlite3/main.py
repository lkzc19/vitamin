import sqlite3


if __name__ == '__main__':
    connection = sqlite3.connect('vitamin.db')
    c = connection.cursor()
    c.execute('CREATE TABLE IF NOT EXISTS v_foo(message text)')
    c.execute('INSERT INTO v_foo (message) VALUES (\'Hello\')')
    c.execute('INSERT INTO v_foo (message) VALUES (\'World\')')
    c.execute('SELECT * FROM v_foo')
    data = c.fetchall()
    print(data)
