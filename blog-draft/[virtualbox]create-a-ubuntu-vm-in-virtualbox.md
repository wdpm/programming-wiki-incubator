[Ubuntu Virtual Machine (VM) Installation on VirtualBox](https://github.com/whitehat-hero7/Ubuntu-Virtual-Machine-VM-Installation-on-VirtualBox)

这篇文章描述了创建一个 ubuntu vm 的过程。后续如果直接 copy 这个 vm，则会发生 DHCP 网络下的 IP 重复问题。

- 修改 cloned vm 的 MAC address 是无效的，并不能让vm重新分配IP。
- 根据 [此文章](https://forums.virtualbox.org/viewtopic.php?p=489397&sid=a:9194b4783d2e5f14767892b23f52b2d3#p489397) 改变
  machine-id，然后重启 vm 实例是有效的。步骤如下：

```bash
sudo rm -f /etc/machine-id
# Note: /var/lib/dbus/machine-id is a soft link of /etc/machine-id

sudo dbus-uuidgen --ensure=/etc/machine-id
```

为了使得不用sudo都输入密码，可以创建admin组并将用户加入该组。
```
wdpm@lb1:~$ sudo groupadd admin
wdpm@lb1:~$ sudo usermod -aG admin wdpm
```