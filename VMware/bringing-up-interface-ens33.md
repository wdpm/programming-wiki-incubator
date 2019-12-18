# Bringing up interface ens33

报错信息：

```bash
[root@vmware0 ~]# systemctl status network
● network.service - LSB: Bring up/down networking
   Loaded: loaded (/etc/rc.d/init.d/network; bad; vendor preset: disabled)
   Active: failed (Result: exit-code) since Wed 2019-12-18 09:48:20 CST; 5min ago
     Docs: man:systemd-sysv-generator(8)
  Process: 901 ExecStart=/etc/rc.d/init.d/network start (code=exited, status=1/FAILURE)

Dec 18 09:48:20 vmware0 systemd[1]: Starting LSB: Bring up/down networking...
Dec 18 09:48:20 vmware0 network[901]: Bringing up loopback interface:  [  OK  ]
Dec 18 09:48:20 vmware0 network[901]: Bringing up interface ens33:  Error: ...n.
Dec 18 09:48:20 vmware0 network[901]: [FAILED]
Dec 18 09:48:20 vmware0 systemd[1]: network.service: control process exited...=1
Dec 18 09:48:20 vmware0 systemd[1]: Failed to start LSB: Bring up/down netw...g.
Dec 18 09:48:20 vmware0 systemd[1]: Unit network.service entered failed state.
Dec 18 09:48:20 vmware0 systemd[1]: network.service failed.
```

原因解析：NetworkManager 处于启动状态，和network冲突。

解决方法：停用 NetworkManager，启动 network。

```bash
[root@vmware0 ~]# service NetworkManager stop
Redirecting to /bin/systemctl stop NetworkManager.service
[root@vmware0 ~]# systemctl start network
```

