FROM python:3.12.3-slim

# 设置工作目录
WORKDIR /app

# 复制 Python 程序文件到镜像中
COPY . .

# 安装 Python 依赖
RUN pip install --no-cache-dir -r requirements.txt \
    && pip install pyuwsgi

# 暴露应用程序使用的端口
EXPOSE 3000

# 指定程序入口点
CMD ["uwsgi", "--http", "127.0.0.1:3000", "--master", "-p", "4", "-w", "app:app"]