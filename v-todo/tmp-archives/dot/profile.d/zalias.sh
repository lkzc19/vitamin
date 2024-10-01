# 别名

# 读取「alias.d」目录下的别名
source $HOME/profile.d/alias.d/custom.sh
source $HOME/profile.d/alias.d/third.sh

# 该文件所在目录
current_dir=$HOME/profile.d

# 比较通用的别名

alias se='source ~/.zshrc'  # 刷新环境变量

alias sl='ls'
alias ks='ls'
alias ll='ls -l --color=auto'
alias la='ls -al --color=auto'

# mv, rm, cp
alias mv='mv -v'
alias rm='rm -i -v'
alias cp='cp -v'

alias chmox='chmod -x'

# hosts
alias hosts='cat /etc/hosts'
alias vhosts='sudo vim /etc/hosts'

# app
alias code='/Applications/Visual\ Studio\ Code.app/Contents/Resources/app/bin/code'
alias text="/Applications/Sublime\ Text.app/Contents/SharedSupport/bin/subl"

# custom
alias zalias='cat $current_dir/zalias.sh'
alias valias='vim $current_dir/zalias.sh'
alias omz='cat $current_dir/oh_my_zsh.sh'
alias vomz='vim $current_dir/oh_my_zsh.sh'
alias zenv='cat $current_dir/zenv.sh'
alias venv='vim $current_dir/zenv.sh'
alias ztoken='cat $current_dir/ztoken.sh'
alias vtoken='vim $current_dir/ztoken.sh'
alias zconf='cat $current_dir/zconf.sh'
alias vconf='vim $current_dir/zconf.sh'
alias zfunc='cat $current_dir/zfunc.sh'
alias vfunc='vim $current_dir/zfunc.sh'

