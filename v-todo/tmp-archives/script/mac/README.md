# 脚本：

- icmd        HEXO个人博客常用命令
- login2cli   MySQL、PG等客户端直接登录脚本，且作为密码备忘录(本地服务)
- si4jj       掘金签到脚本 shell封装 方便调用
- zimg        Github图床 shell封装 方便调用
- zmdot-sync  Mac环境变量配置同步至仓库的脚本

# 配置
需要将本目录配置到环境变量中 -> ZBIN_HOME

- zbin.conf   一些配置用于shell脚本中读取 仓库中上传了一个zbin.example.conf作为例子，须改名为zbin.conf

# 依赖

- si4jj.py        掘金签到脚本
- zimg-gitee.py   gitee图床
- zimg-github.py  github图床

# 模版

- xyz.drinkice.blasphemy.si4jj.plist      Mac开机任务，延迟N分钟后执行掘金签到脚本
