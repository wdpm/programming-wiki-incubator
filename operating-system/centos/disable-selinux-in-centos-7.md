# disable selinux in CentOS 7
```bash
nano /etc/sysconfig/selinux
```
```
# This file controls the state of SELinux on the system.
# SELINUX= can take one of these three values:
#     enforcing - SELinux security policy is enforced.
#     permissive - SELinux prints warnings instead of enforcing.
#     disabled - No SELinux policy is loaded.
SELINUX=disabled
# SELINUXTYPE= can take one of three two values:
#     targeted - Targeted processes are protected,
#     minimum - Modification of targeted policy. Only selected processes are protected.
#     mls - Multi Level Security protection.
SELINUXTYPE=targeted
```

## tutorial

- [CentOS常用基础命令](https://github.com/jaywcjlove/handbook/blob/master/docs/CentOS/CentOS.md)
- [CentOS7网络配置](https://github.com/jaywcjlove/handbook/blob/master/docs/CentOS/CentOS7网络配置.md)