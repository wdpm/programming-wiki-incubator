# VMware克隆CentOS 7 VM

## 克隆

1. 停止已有CentOS 虚拟机。

2. 选择已有CentOS 虚拟机 -> 右键 “管理” -> 克隆。克隆自：**虚拟机当前状态**，克隆方法：**创建完整克隆**。选择合适的磁盘文件夹，保存。

3. 克隆完成后，直接启动克隆的虚拟机。

## 配置

配置全新物理静态IP

```bash
nano /etc/sysconfig/network-scripts/ifcfg-ens33
```

```bash
...
IPADDR=192.168.31.13
...
```

仅更改IP即可。然后reboot。

重启后，使用远程SSH工具尝试登录。如果无法登录，尝试排查该VM的firewall以及sshd是否开启。

