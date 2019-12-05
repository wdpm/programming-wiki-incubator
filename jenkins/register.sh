#!/bin/sh

psid=0
APP_PORT=8100
APP_NAME=/root/blog/register/target/register.jar

checkPid(){
  javaps=`jps -l | grep $APP_NAME`
  if[ -n "$javaps" ];then
    psid=`echo $javaps | awk '{print $1}'`
  else
    psid=0
  fi
}

start(){
  checkPid
  if[ $psid -ne 0]l;then
    echo "========================"
    echo "warning: $APP_NAME is running.(pid=$psid)"
    echo "========================"
  else
    echo -n "staring $APP_NAME"
    nohup java -jar $APP_NAME > nohup.out &
    checkPid
    if[ $psid -ne 0];then
      echo "(pid=$psid) is ok."
    else
      echo "start $APP_NAME failed."
    fi
  fi
}

case "$1" in
  'start')
       start
       ;;
  *)
       "echo "usage: $0 {start}"
       exit 1
       ;;
esac

exit 0
