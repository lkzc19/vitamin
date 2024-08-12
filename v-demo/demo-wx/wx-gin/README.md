# demo-wx-gin

用于微信相关 如公众号来接收事件

## 依赖库

- [PowerWechat](https://powerwechat.artisan-cloud.com/zh/start/) 微信第三方SDK。Github上star第二，文档齐全，目前仍在维护更新。

## 程序编译与运行时出现的问题

编译时，注意要符合要运行机器的系统及其CPU架构，否则可能运行不了。如果要放在docker里运行，直接用docker镜像作为编译环境，如此项目的Dockerfile。

```bash
exec ./app: exec format error
```