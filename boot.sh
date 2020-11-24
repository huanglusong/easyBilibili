#!/usr/bin/env bash
# 这个脚本用来添加定时任务
app_dir="/root/others/easybilibili"
# 设置定时任务
function set_crontab(){
    crontab_file="/var/spool/cron/crontabs/${USER}"
    # 如果当前用户没有创建定时任务，则创建
    if [ ! -f ${crontab_file} ]; then
        touch crontab_file
    fi
    # 往crontab文件中追加任务
    # 如果添加过相关的任务先删除 使用sed命令
    sed -i '/easyBilibili/d' ${crontab_file}
    # 添加新的定时任务
    echo "# easyBilibili定时任务" >> ${crontab_file}
    echo "30 10 * * * bash ${app_dir}/task.sh" >> ${crontab_file}
	  service cron reload
}

set_crontab