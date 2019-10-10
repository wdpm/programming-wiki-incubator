# setup shadowsocks server in centos7

## precondition
``` bash
yum install python-setuptools && easy_install pip 
pip install shadowsocks

sudo mkdir /etc/shadowsocks
sudo nano /etc/shadowsocks/shadowsocks.json
```

### single user
``` json
{
    "server":"your_server_ip",
    "server_port":443,
    "local_address": "127.0.0.1",
    "local_port":1080,
    "password":"your_password",
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open": true,
    "workers":1
}
```

### multi users
``` json
{
    "server":"your_server_ip",
    "local_address":"127.0.0.1",
    "local_port":1080,
    "port_password":{
         "9001":"your_password_1",
         "9002":"your_password_2",
         "9003":"your_password_2"
    },
    "timeout":300,
    "method":"aes-256-cfb",
    "fast_open": false
}
```
## disable firewall
```bash
systemctl disable firewalld
systemctl stop firewalld
```

## start shadowsocks server
``` bash
ssserver -c /etc/shadowsocks/shadowsocks.json -d start
ssserver -c /etc/shadowsocks/shadowsocks.json -d stop

// start it after boot
nano /etc/rc.local
ssserver -c /etc/shadowsocks/shadowsocks.json -d start
# use echo to append
echo 'ssserver -c /etc/shadowsocks/shadowsocks.json -d start' >> /etc/rc.local
```
> Please note that you must run 'chmod +x /etc/rc.d/rc.local' to ensure that this script will be executed during boot.

## enable BBR 
> BBR project url£ºhttps://github.com/google/bbr

### upgrade linux kernel
``` bash
yum update
# cat /etc/redhat-release 

# install newer linux kernel
rpm --import https://www.elrepo.org/RPM-GPG-KEY-elrepo.org
rpm -Uvh http://www.elrepo.org/elrepo-release-7.0-2.el7.elrepo.noarch.rpm
yum --enablerepo=elrepo-kernel install kernel-ml -y

# check linux kernel version
egrep ^menuentry /etc/grub2.cfg | cut -f 2 -d \'

# reboot to make effect
grub2-set-default 0
reboot

# check again
uname -r

# enable bbr in kernel
nano /etc/sysctl.conf
# add this two lines to the end
net.core.default_qdisc = fq
net.ipv4.tcp_congestion_control = bbr

# reload linux kernel
sysctl -p

# check 
sysctl net.ipv4.tcp_available_congestion_control
lsmod | grep bbr
```