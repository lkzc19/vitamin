import psycopg2

import config.PgConfig as PgConfig


class PgUtils:
    def __init__(self):
        self.host = PgConfig.get_host()
        self.port = PgConfig.get_port()
        self.database = PgConfig.get_database()
        self.user = PgConfig.get_user()
        self.password = PgConfig.get_password()
        self.conn = None

    def __enter__(self):
        self.connect()
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.close_connection()

    def connect(self):
        self.connection = psycopg2.connect(
            host=self.host,
            port=self.port,
            database=self.database,
            user=self.user,
            password=self.password
        )

    def execute_query(self, query, params=None):
        if not self.connection:
            self.connect()

        cursor = self.connection.cursor()
        if params:
            cursor.execute(query, params)
        else:
            cursor.execute(query)

        result = cursor.fetchall()
        cursor.close()

        return result

    def close_connection(self):
        if self.connection:
            self.connection.close()
            self.connection = None
