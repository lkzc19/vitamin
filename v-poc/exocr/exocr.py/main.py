from dotenv import load_dotenv
import os

load_dotenv()

app_key = os.getenv('APPKey')
app_secret = os.getenv('APPSecret')

print(app_key)
print(app_secret)