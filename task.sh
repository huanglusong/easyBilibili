#!/usr/bin/env bash
app_dir="/root/others/easybilibili"
log_dir="${app_dir}/logs"
sessdata=
DedeUserID=
bili_jct=

sckey=

log_time=$(date "+%Y-%m-%d-%H-%M-%S")
log_file="${log_dir}/${log_time}.log"
[[ -z $sessdata ]] || [[ -z $DedeUserID ]] || [[ -z $bili_jct ]] && echo -e "请完整输入必填项" >> ${log_file} 2>&1 && exit 1
if [ -z ${sckey} ]; then
  java -jar ${app_dir}/easyBilibili-1.0.0.jar ${sessdata} ${DedeUserID} ${bili_jct} >>${log_file} 2>&1 &
else
  java -jar ${app_dir}/easyBilibili-1.0.0.jar ${sessdata} ${DedeUserID} ${bili_jct} ${sckey} >>${log_file} 2>&1 &
fi
