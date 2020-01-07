# Ubuntu实用技巧

## 设置环境变量

Ubuntu下系统环境变量配置文件：/etc/profile 

Ubuntu下个人用户目录下的环境变量配置文件：～/.profile

- 编辑～/.profile，退出终端就无效

```bash
sudo gedit ~/.profile
# 例如设置Android开发环境变量
export ANDROID_HOME="/home/wdpm/Android/Sdk"
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
source ~/.profile
```

- 编辑～/.bashrc，退出终端依然有效

```bash
sudo gedit ~/.bashrc
# vim somefiles
source ~/.bashrc
```

## 文件夹大小排序

```bash
du -k --max-depth=1|sort -rn
```

## apt无法获得锁

> 无法获得锁 /var/lib/dpkg/lock - open 

删除锁文件:

```bash
sudo rm /var/lib/apt/lists/lock
```

可能还需要删除缓存目录的锁文件：

```bash
sudo rm /var/cache/apt/archives/lock
sudo rm /var/lib/dpkg/lock
```

或者:

```bash
rm /var/lib/dpkg/lock
dpkg --configure -a
```

## 删除不常用软件

删除系统自带软件

```bash
sudo apt -y purge [sofeware-name]
```

删除第三方软件

```bash
sudo apt remove [sofeware-name]
```

## 桌面主题美化

桌面环境为unity

- 主题管理工具：unity-tweak-tool
- 主题：Flatabulous
- 图标：Ultra-flat
- 指针：DMz-white
- 字体：文泉驿微米黑
- 状态栏系统信息指示器：indicator-sysmonitor
- 图标位于屏幕中下方（可选）：docky

其他优秀的主题：`Adapta` `Flat Plat` `VimixLight`

其他优秀的图标：`Numix`

## sudo命令非常慢

原因：/etc/hosts被修改，缺失本机名称条目，导致ubuntu需要时间解析。

解决方案：

1.获取本机名称

```bash
hostname
# hp2341
```

2.修改`/etc/hosts`

```bash
sudo gedit /etc/hosts
127.0.0.1    localhost hp2341  <---添加本机名称
::1    localhost hp2341  <---添加本机名称
```