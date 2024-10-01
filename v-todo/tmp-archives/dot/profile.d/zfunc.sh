#!/bin/bash

# 创建目录并进入
function mdc() {
  mkdir -p "$@" && cd "$@"
}

# 查看历史命令，且过滤掉history命令所产生的命令
function hg() {
	history | grep -v "history" | grep -v "hg" | grep "$@"
}

# 查看进程，且过滤掉本条命令的进程
function pg() {
	ps -ef | grep -v "grep" | grep "$@"
}



