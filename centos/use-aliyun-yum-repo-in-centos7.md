# Use Aliyun Yum Repo in CentOS7
## backup old repo
``` bash
mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup
```

## add aliyun base repo
```bash
wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
```

## add aliyun epel repo
``` bash
wget -O /etc/yum.repos.d/epel-7.repo http://mirrors.aliyun.com/repo/epel-7.repo
```

## yum clean and make cache
```
yum clean all
yum makecache
```