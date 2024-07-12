# v-docker

- 存放开发时使用到的一些组件，使用docker快速部署进行测试。
- 存放平时工作中使用到的组件，做一个存档，方便使用。

# dockerfile

```bash
docker build -t demo-pgsql -f pgsql.Dockerfile .
docker run --name demo-pgsql -p 3432:5432 -d demo-pgsql
```

# docker-compose

```bash
# 启动
docker-compose up

# 停止并删除应用程序的所有服务
docker-compose down

# 重启应用程序的所有服务
docker-compose restart

# 查看应用程序的日志
docker-compose logs

# 调整某个服务的副本数量
docker-compose scale

# 验证并查看当前的 Docker Compose 配置
docker-compose config

# 构建或重新构建服务的镜像
docker-compose build

# 启动已存在的服务容器
docker-compose start

# 停止已运行的服务容器
docker-compose stop

# 删除停止的服务容器
docker-compose rm
```