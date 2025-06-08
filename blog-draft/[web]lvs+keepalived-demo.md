

# lvs + keepalived demo

## pre-conditions

服务器需求：

- 2台Director节点（主备调度器，运行LVS+Keepalived） 
- 2台RealServer节点（运行Web服务，如Nginx或Apache）

网络架构：

- 所有服务器需在同一个私有网络内。
- VIP（虚拟IP）使用私有网络内的一个未占用IP。
- 主备Director通过VRRP协议管理VIP漂移。

实验环境：使用virtualbox创建虚拟机在本地进行模拟。

| HOSTNAME | IP             | 说明               |
| -------- | -------------- | ------------------ |
| LB1      | 192.168.56.101 | Keepalived主服务器 |
| LB2      | 192.168.56.102 | Keepalived备服务器 |
| RS1      | 192.168.56.103 | web01服务器        |
| RS2      | 192.168.56.104 | web02服务器        |

网络拓扑图

```
                              |
             +----------------+-----------------+
             |                                  |
192.168.56.101|----    VIP:192.168.56.200   ----|192.168.56.102
     +-------+--------+                +--------+-------+
     | 	    DS1       |                |       DS2      |
     | LVS+Keepalived |                | LVS+Keepalived |
     +-------+--------+                +--------+-------+
             |			                |
             +----------------+-----------------+
                              |
  +------------+              |               +------------+
  |     RS1    |192.168.56.103| 192.168.56.104|     RS2    |
  | Web Server +--------------+---------------+ Web Server |
  +------------+                              +------------+
```

## setup LB1 and LB2

```
sudo apt install keepalived ipvsadm -y  # Ubuntu
```

DS1(MASTER) 节点

```
nano /etc/keepalived/keepalived.conf
```

```conf
! Configuration File for keepalived
global_defs {
   router_id LVS_MASTER
}

vrrp_instance VI_1 {
    state MASTER            # 两个 DS，一个为 MASTER 一个为 BACKUP
    interface enp0s8        # 当前 IP 对应的网络接口，通过 ifconfig 查询
    virtual_router_id 62    # 虚拟路由 ID(0-255)，在一个 VRRP 实例中主备服务器 ID 必须一样
    priority 200            # 优先级值设定：MASTER 要比 BACKUP 的值大
    advert_int 1            # 通告时间间隔：单位秒，主备要一致
    authentication {        # 认证机制，主从节点保持一致即可
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.200       # VIP，可配置多个
    }
}

# LB 配置
virtual_server 192.168.56.200 80  {
    delay_loop 3                    # 设置健康状态检查时间
    lb_algo rr                      # 调度算法，这里用了 rr 轮询算法
    lb_kind DR                      # 这里测试用了 Direct Route 模式
    persistence_timeout 50          # 持久连接超时时间
    protocol TCP
	real_server 192.168.56.103 80 {
        weight 1
        TCP_CHECK {
            connect_timeout 10　　　
            retry 3　　　　　　      # 旧版本为 nb_get_retry 
            delay_before_retry 3　　　
            connect_port 80
        }
    }
	 real_server 192.168.56.104 80 {
        weight 1
        TCP_CHECK {
            connect_timeout 10
            retry 3
            delay_before_retry 3
            connect_port 80
        }
    }
}
```

DS2(BACKUP) 节点

```
! Configuration File for keepalived
global_defs {
   router_id LVS_BACKUP
}

vrrp_instance VI_1 {
    state BACKUP            # 两个 DS，一个为 MASTER 一个为 BACKUP
    interface enp0s8        # 当前 IP 对应的网络接口，通过 ifconfig 查询
    virtual_router_id 62    # 虚拟路由 ID(0-255)，在一个 VRRP 实例中主备服务器 ID 必须一样
    priority 100            # 优先级值设定：MASTER 要比 BACKUP 的值大
    advert_int 1            # 通告时间间隔：单位秒，主备要一致
    authentication {        # 认证机制，主从节点保持一致即可
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.200       # VIP，可配置多个
    }
}

# LB 配置
virtual_server 192.168.56.200 80  {
    delay_loop 3                    # 设置健康状态检查时间
    lb_algo rr                      # 调度算法，这里用了 rr 轮询算法
    lb_kind DR                      # 这里测试用了 Direct Route 模式
    persistence_timeout 50          # 持久连接超时时间
    protocol TCP
	real_server 192.168.56.103 80 {
        weight 1
        TCP_CHECK {
            connect_timeout 10　　　
            retry 3　　　　　　      # 旧版本为 nb_get_retry 
            delay_before_retry 3　　　
            connect_port 80
        }
    }
	 real_server 192.168.56.104 80 {
        weight 1
        TCP_CHECK {
            connect_timeout 10
            retry 3
            delay_before_retry 3
            connect_port 80
        }
    }
}
```

在DS1和DS2上面分别运行此命令，重载设置

```
systemctl restart keepalived
```



## 验证LB初始化

### 确认VIP绑定满足期望

LB1的IP

```bash
root@lb1:~# ip a
3: enp0s8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP group default qlen 1000
    link/ether 08:00:27:fb:6e:21 brd ff:ff:ff:ff:ff:ff
    inet 192.168.56.101/24 metric 100 brd 192.168.56.255 scope global dynamic enp0s8
       valid_lft 362sec preferred_lft 362sec
    inet 192.168.56.200/32 scope global enp0s8
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fefb:6e21/64 scope link
       valid_lft forever preferred_lft forever

```

LB2的IP

```bash
wdpm@lb2:~$ ip a
3: enp0s8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP group default qlen 1000
    link/ether 08:00:27:43:3f:39 brd ff:ff:ff:ff:ff:ff
    inet 192.168.56.102/24 metric 100 brd 192.168.56.255 scope global dynamic enp0s8
       valid_lft 535sec preferred_lft 535sec
    inet6 fe80::a00:27ff:fe43:3f39/64 scope link
       valid_lft forever preferred_lft forever

```

可以看到VIP目前是绑定到LB1，LB2没有绑定VIP。原因是上面的例子中为主备模式，LB1是主，而且优先级较高。

### 验证VIP漂移

接下来，我们想要验证：

- 主动挂掉LB1（主），LB2（备）能否感知故障并顺利接管VIP。
- 恢复LB1（主），LB1能否凭借高优先级重新夺回VIP。

实验1：

- 首先在LB2 监听系统日志

```bash
tail -f /var/log/syslog
```

- 接着在LB1停掉keepalived服务，模拟故障

```bash
root@lb1:~# systemctl stop keepalived.service
```

- 此时观察LB2的系统日志，

```bash
wdpm@lb2:~$ tail -f /var/log/syslog
Jun  5 09:24:10 lb1 systemd-networkd[673]: enp0s8: DHCP: No gateway received from DHCP server.
Jun  5 09:28:24 lb1 Keepalived_vrrp[2264]: (VI_1) Backup received priority 0 advertisement
Jun  5 09:28:24 lb1 Keepalived_vrrp[2264]: (VI_1) Entering MASTER STATE
```

​        并检查LB2的IP，可以看到VIP的确绑定到了LB2。

```bash
wdpm@lb2:~$ ip a
3: enp0s8: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP group default qlen 1000
    link/ether 08:00:27:43:3f:39 brd ff:ff:ff:ff:ff:ff
    inet 192.168.56.102/24 metric 100 brd 192.168.56.255 scope global dynamic enp0s8
       valid_lft 414sec preferred_lft 414sec
    inet 192.168.56.200/32 scope global enp0s8
       valid_lft forever preferred_lft forever
    inet6 fe80::a00:27ff:fe43:3f39/64 scope link
       valid_lft forever preferred_lft forever
```

- 然后，我们恢复LB1的keepalived服务。可以看到LB2收到了来自LB1的通知，并对比优先级，进入BACKUP状态。

```bash
wdpm@lb2:~$ tail -f /var/log/syslog
Jun  5 09:34:25 lb1 Keepalived_vrrp[2264]: (VI_1) Master received advert from 192.168.56.101 with higher priority 200, ours 100
Jun  5 09:34:25 lb1 Keepalived_vrrp[2264]: (VI_1) Entering BACKUP STATE
```

如果此时，再次检测两者的IP，会发现LB1夺回VIP，LB2不再持有VIP。这是非常理想的情况。  



### 补充日志

此时，下游的RS都还没有准备好。日志中可以看到：

```bash
root@lb1:~# journalctl -u keepalived -f
-- Logs begin at Thu 2025-06-05 03:42:13 UTC. --
Jun 05 08:28:57 lb1 Keepalived_healthcheckers[22214]: Gained quorum 1+0=1 <= 2 for VS [192.168.56.200]:tcp:80
Jun 05 08:28:57 lb1 Keepalived_healthcheckers[22214]: Activating healthchecker for service [192.168.56.103]:tcp:80 for VS [192.168.56.200]:tcp:80
Jun 05 08:28:57 lb1 Keepalived_healthcheckers[22214]: Activating healthchecker for service [192.168.56.104]:tcp:80 for VS [192.168.56.200]:tcp:80
Jun 05 08:28:57 lb1 Keepalived_healthcheckers[22214]: Activating BFD healthchecker
Jun 05 08:29:01 lb1 Keepalived_vrrp[22216]: (VI_1) Entering MASTER STATE
Jun 05 08:29:02 lb1 Keepalived_healthcheckers[22214]: TCP_CHECK on service [192.168.56.104]:tcp:80 failed.
Jun 05 08:29:02 lb1 Keepalived_healthcheckers[22214]: Removing service [192.168.56.104]:tcp:80 to VS [192.168.56.200]:tcp:80
Jun 05 08:29:03 lb1 Keepalived_healthcheckers[22214]: TCP_CHECK on service [192.168.56.103]:tcp:80 failed.
Jun 05 08:29:03 lb1 Keepalived_healthcheckers[22214]: Removing service [192.168.56.103]:tcp:80 to VS [192.168.56.200]:tcp:80
Jun 05 08:29:03 lb1 Keepalived_healthcheckers[22214]: Lost quorum 1-0=1 > 0 for VS [192.168.56.200]:tcp:80
```



## setup RS1 and RS2

分别在两台机器上安装并启动nginx

```
sudo apt install nginx
sudo service nginx start
```

为了方便识别RS1和RS2，最好是把nginx那个index.html页面内容增加RS？标记文本。

记得放行80端口供外界访问，或者禁用ufw。

```
sudo ufw status
sudo ufw allow 80/tcp  
sudo ufw reload
```

保证先把网络工具安装

```
apt install net-tools
```

在 RS 的网卡上配置 lo 为 VIP。新建一个脚本 `lvs-web.sh`。

这里主要是配置 **ARP 抑制**：

```bash
#!/bin/bash
SNS_VIP=192.168.56.200
case "$1" in
start)
    # Ubuntu 24.04 使用 ip 命令替代 CentOS 7 的 ifconfig 和 route
    ip addr add $SNS_VIP/32 dev lo label lo:0
    ip link set dev lo:0 up
    echo "1" > /proc/sys/net/ipv4/conf/lo/arp_ignore
    echo "2" > /proc/sys/net/ipv4/conf/lo/arp_announce
    echo "1" > /proc/sys/net/ipv4/conf/all/arp_ignore
    echo "2" > /proc/sys/net/ipv4/conf/all/arp_announce
    sysctl -p >/dev/null 2>&1
    echo "RealServer Start OK"
    ;;
stop)
    ip addr del $SNS_VIP/32 dev lo:0
    ip link set dev lo:0 down
    echo "0" > /proc/sys/net/ipv4/conf/lo/arp_ignore
    echo "0" > /proc/sys/net/ipv4/conf/lo/arp_announce
    echo "0" > /proc/sys/net/ipv4/conf/all/arp_ignore
    echo "0" > /proc/sys/net/ipv4/conf/all/arp_announce
    echo "RealServer Stopped"
    ;;
*)
    echo "Usage: $0 {start|stop}"
    exit 1
esac
exit 0
```

赋予脚本执行权限并执行。

```
chmod +x lvs-web.sh
# 注意这里必须使用sudo来运行，否则可能会权限失败
sudo ./lvs-web.sh start
```

到了这里。RS就准备好了。



## 验证RS负载均衡

我们回到 LB1 示例，观察LVS的负载均衡状态。

```bash
wdpm@lb1:~$ sudo ipvsadm -Ln --stats
IP Virtual Server version 1.2.1 (size=4096)
Prot LocalAddress:Port               Conns   InPkts  OutPkts  InBytes OutBytes
  -> RemoteAddress:Port
TCP  192.168.56.200:80                   0        0        0        0        0
  -> 192.168.56.103:80                   0        0        0        0        0
  -> 192.168.56.104:80                   0        0        0        0        0
```

可以看到下游的两个RS都被感知到了，而且服务状态正常。



### 测试RS负载均衡结果

执行下面脚本，多次访问目标VIP。由于RS是轮询策略，因此应该是交替出现RS1、RS2文本内容。

```bash
while true ; do curl 192.168.56.200; sleep 1;done
```

发现流量全部走到了RS1，显示RS1的文本。这可能是因为持久化超时这一行的作用强于rr的轮询策略，因此轮询没有生效。

```
virtual_server 192.168.56.200 80  {
    persistence_timeout 50
}
```

将这行注释掉，在宿主OS开启terminal循坏访问，RS1和RS2终于交替出现。





## 参考

- https://www.cnblogs.com/Sinte-Beuve/p/13392747.html
- [Linux实战教学笔记31：Keepalived高可用集群应用实践 - 陈思齐 - 博客园](https://www.cnblogs.com/chensiqiqi/p/9162934.html)

