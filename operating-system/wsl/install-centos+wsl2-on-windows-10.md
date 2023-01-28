# 在 win 10上安装 WSL 2和CentOS

1. 下载centos-wsl 特供镜像，https://github.com/mishamosher/CentOS-WSL/releases/tag/7.9-2009，版本可以按需寻找。

2. zip解压后，执行里面的CentOS8.exe程序，等待安装完毕。

3. 现在由于centos已经提桶跑路了，所以需要使用归档的镜像软件源来提供更新支持。

   ```bash
   cd /etc/yum.repos.d/
   
   sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
   sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*
   
   wget -O /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo
   yum clean all
   yum makecache
   
   # test install software
   yum install wget –y
   ```

4. 安装neofetch

   ```bash
   yum install epel-release -y
   yum install neofetch -y
   
   neofetch
   ```

5. 激活systemd命令的支持。

   ```bash
   sudo nano /etc/wsl.conf
   
   # 将下面的内容写入，注意去掉开头的注释符号#
   #[boot]
   #systemd=true
   
   # On host os
   wsl.exe --shutdown
   
   # on centos, check systemd support
   systemctl list-unit-files --type=service
   ```

   

