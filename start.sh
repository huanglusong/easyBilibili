#!/usr/bin/env bash
# 这个脚本用来下载需要的jar包和boot脚本

root_dir="$( cd $( dirname $0 ); pwd )"
log_dir="${root_dir}/logs"
log_time=$(date "+%Y-%m-%d-%H-%M-%S")
log_file="${log_dir}/${log_time}.log"

function check_root(){
    [[ $EUID != 0 ]] && echo -e "当前账号非ROOT(或没有ROOT权限)，无法继续操作，请切换至root账户后继续操作" && exit 1
}


# 创建logs目录
function make_log_dir(){
    if [ ! -d ${log_dir} ]; then
        mkdir -p ${log_dir}
    fi   
}
# 安装java工具
function install_java(){
    command -v apt >/dev/null 2>&1 && (apt-get update; apt-get install openjdk-8-jdk -y; return;)
    command -v yum >/dev/null 2>&1 && (yum install java-1.8.0-openjdk -y; return;)
}
# 安装wget
function install_wget(){
    command -v apt >/dev/null 2>&1 && (apt-get update; apt-get install wget -y; return;)
    command -v yum >/dev/null 2>&1 && (yum install wget -y; return;)
}
# 下载easy bilibili 的jar包
function download(){
    # 检查有没有安装wget工具，没有则安装下
    command -v wget > /dev/null 2>&1 || install_wget
    # 检查java
    command -v java > /dev/null 2>&1 || install_java
    # 默认安装在当前目录下
    # 获取jar包
    wget "https://gitee.com/huanglusong/easy-bilibili/attach_files/523064/download/easyBilibili-1.0.0.jar"
    # 获取boot脚本
    wget "https://gitee.com/huanglusong/easy-bilibili/raw/master/boot.sh"
    # 获取task脚本
    wget "https://gitee.com/huanglusong/easy-bilibili/raw/master/task.sh"

}
check_root
make_log_dir
download


