#!/bin/sh

psid=0
APP_PORT=8100
APP_NAME=/app/blog/register.jar

checkPid(){
  javaps=`jps -l | grep ${APP_NAME}`
  if [[ -n ${javaps} ]];then
    psid=`echo ${javaps} | awk '{print $1}'`
  else
    psid=0
  fi
}

start(){
  checkPid
  if [[ ${psid} -ne 0 ]];then
    echo "========================"
    echo "warning: $APP_NAME is running.(pid=$psid)"
    echo "========================"
    echo "Now restart..."
    restartApp
  else
    startApp
  fi
}

startApp(){
    echo -e "staring $APP_NAME."
    nohup java -jar ${APP_NAME} > nohup.out &
    checkPid
    if [[ ${psid} -ne 0 ]];then
      echo "(pid=$psid) is ok."
    else
      echo "start $APP_NAME failed."
    fi
}

stopApp(){
  checkPid
  if [ $psid -ne 0 ]; then
    kill -9 $psid
    # double check if stop successfully
    if [ $? -eq 0 ]; then
      echo "Stop $APP_NAME ok."
    else
      echo "Stop $APP_NAME failed."
    fi
  else
    echo "$APP_NAME is not running. Don't need to stop."
  fi
}

restartApp(){
  stopApp
  startApp
}

case "$1" in
  'start')
       start
       ;;
  *)
       echo "usage: $0 {start}"
       exit 1
esac

exit 0