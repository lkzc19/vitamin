# CORS 测试

[gin](https://gin-gonic.com/zh-cn/)框架, 使用官方的[gin-cors](https://github.com/gin-contrib/cors)做跨域处理

## 接口

### ping

```markdown
GET 127.0.0.1:3000/ping
参数 无
返回 "pong"
```

### foo

```markdown
POST 127.0.0.1:3000/foo
参数 body {name: "胡桃"}
返回 "bar"
```

### cors-bug

```markdown
POST 127.0.0.1:3000/cors-bug
参数 body {name: "纳西妲"}
返回 "cors-bug"
```

## 说明

接口`ping`|`foo`做了跨域处理, `cors-bug`未做跨域处理