# CORS 测试

[gin](https://gin-gonic.com/zh-cn/)框架, 使用官方的[gin-cors](https://github.com/gin-contrib/cors)做跨域处理

## 接口

### ping

```markdown
GET 127.0.0.1:3000/ping
返回 "pong"
```

### normal

```markdown
POST 127.0.0.1:3000/normal
body {name: "胡桃"}
返回 "bar"
```

### XNahida

```markdown
POST 127.0.0.1:3000/XNahida
headers {X-Nahida: "xx"}
body {name: "胡桃"}
返回 "bar"
```

### XHutao

```markdown
POST 127.0.0.1:3000/Xhutao
headers {X-Hutao: "xx"}
body {name: "胡桃"}
返回 "bar"
```

### cors-bug

```markdown
POST 127.0.0.1:3000/cors-bug
body {name: "纳西妲"}
返回 "cors-bug"
```

## 说明

接口`ping`|`normal`做了跨域处理, `cors-bug`未做跨域处理

`cors`中间件加了`header`加了`X-Nahida`

接口`XNahida`传请求头`X-Nahida`不会出现跨域问题
接口`Xhutao`传请求头`X-Hutao`会出现跨域问题