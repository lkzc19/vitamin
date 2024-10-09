from dotenv import load_dotenv
import uuid
import oss2
from oss2.credentials import EnvironmentVariableCredentialsProvider


load_dotenv()
auth = oss2.ProviderAuth(EnvironmentVariableCredentialsProvider())
bucket = oss2.Bucket(auth, 'https://oss-cn-shanghai.aliyuncs.com', 'ckmro')


if __name__ == '__main__':
    uuid1 = uuid.uuid1()
    key = f'dev/ckmro/apps/test/{uuid1}'
    bucket.put_object_from_file(key, "./img/1.png")