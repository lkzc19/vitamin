# my env in profile.d
for i in ~/profile.d/*.sh ; do
    if [ -r "$i" ]; then
        if [ "${-#*i}" != "$-" ]; then
            . "$i"
        else
            . "$i" >/dev/null 2>&1
        fi
    fi
done

# 首次登录刷新
if [[ -z "$ZSH_LOADED" ]]; then
    export ZSH_LOADED="true"
    source ~/.zshrc
fi

