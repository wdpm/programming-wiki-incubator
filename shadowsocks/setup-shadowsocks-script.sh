#!/bin/sh
# prepare...
yum install python-setuptools && easy_install pip 
pip install shadowsocks

# create shadowsocks configuration file
sudo mkdir -p /etc/shadowsocks
sudo touch /etc/shadowsocks/shadowsocks.json

# get ip 
# method1
#yum install net-tools -y
#SERVER_IP=$(ifconfig eth0 |grep '\binet\b' | awk '{print $2}')

# method2
SERVER_IP=$(curl -s ipinfo.io | grep "\bip\b"| awk '{print $2}' |sed 's/\"//g;s/,//g')

echo ${SERVER_IP}

# update shadowsocks configuration file
echo '{' > /etc/shadowsocks/shadowsocks.json
echo "    \"server\"":"\"${SERVER_IP}\"", >> /etc/shadowsocks/shadowsocks.json
echo '    "local_address":"127.0.0.1",
    "local_port":1080,
    "port_password":{
         "9001":"2019vpn",
         "9002":"2019vpn",
         "9003":"2019vpn"
    },
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open": false
}' >> /etc/shadowsocks/shadowsocks.json

# disbale & stop firewalld
systemctl disable firewalld && systemctl stop firewalld

# start ssserver
ssserver -c /etc/shadowsocks/shadowsocks.json -d start
