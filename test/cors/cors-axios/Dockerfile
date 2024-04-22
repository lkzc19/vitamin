FROM nginx:stable-alpine3.17-slim

COPY dist/* /var/www/html
COPY default.conf /etc/nginx/conf.d

# 暴露 Nginx 默认端口
EXPOSE 80

# 启动 Nginx 服务器
CMD ["nginx", "-g", "daemon off;"]
