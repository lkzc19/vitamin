# v-kotlin/java

- [ ] nio

# v-go

- [ ] lib - tcell
- [ ] lib - gonum

# p-gof





下面介绍如何使用Prometheus、Grafana、CAdvisor、node-exporter、mysqld-exporter对本机服务器性能、Docker容器、MySQL数据库进行监控。

监控本机，只需要一个exporter
node_exporter – 用于机器系统数据收集
mysqld-exporter 用于MySQL数据库数据收集
Cadvisor 用于收集宿主机上的docker容器数据
Grafana是一个开源的功能丰富的数据可视化平台，通常用于时序数据的可视化。

微软免费的埋点工具【clarity】