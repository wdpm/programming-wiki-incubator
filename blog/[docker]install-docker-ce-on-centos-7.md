# install docker ce on centos 7

> refer: https://docs.docker.com/v17.12/install/linux/docker-ce/centos/#install-using-the-repository 

## 准备

```bash
# Install required packages
sudo yum install -y yum-utils device-mapper-persistent-data lvm2

# set up the stable repository
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
# you can check /etc/yum.repos.d/docker-ce.repo for more detail.
```

## 安装Docker-CE

### 安装最新CE版本

```bash
# install the latest version of Docker CE
sudo yum install docker-ce
```

但是这个命令默认安装最新版本的docker-ce，随着时间的推移，最新版本的具体值也在变化。也就是说，最终安装的版本结果不可控。

### 安装特定的CE版本

```bash
[root@vmware0 ~]# yum list docker-ce --showduplicates | sort -r
 * updates: mirrors.cqu.edu.cn
Loading mirror speeds from cached hostfile
Loaded plugins: fastestmirror, langpacks
 * extras: mirrors.cqu.edu.cn
docker-ce.x86_64            3:19.03.5-3.el7                     docker-ce-stable
docker-ce.x86_64            3:19.03.4-3.el7                     docker-ce-stable
docker-ce.x86_64            3:19.03.3-3.el7                     docker-ce-stable
docker-ce.x86_64            3:19.03.2-3.el7                     docker-ce-stable
docker-ce.x86_64            3:19.03.1-3.el7                     docker-ce-stable
...
```

```bash
sudo yum install <FULLY-QUALIFIED-PACKAGE-NAME>
```

以 3:19.03.5-3.el7 为例，全限定名称为: docker-ce-19.03.5。

## 启动

在启动之前，关闭 selinux 以及 firewall。

```bash
sudo systemctl start docker
sudo systemctl status docker
sudo docker run hello-world
```



