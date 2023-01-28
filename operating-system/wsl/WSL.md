# WSL2

## installation
[安装](https://learn.microsoft.com/en-us/windows/wsl/install)

## Set up a WSL development environment
[建立开发环境](https://learn.microsoft.com/en-us/windows/wsl/setup/environment?source=recommendations)

## 启用 systemd
```python

```

## 网络无法上网
```bash
wsl --shutdown
netsh winsock reset
netsh int ip reset all
netsh winhttp reset proxy
ipconfig /flushdns
```

## 迁移到非C盘
```bash
 wsl -l -v
  NAME                   STATE           VERSION
* docker-desktop         Stopped         2
  Ubuntu-22.04           Stopped         2
  docker-desktop-data    Stopped         2
```
```bash
 wsl --shutdown
 wsl --export Ubuntu-22.04 D:/WSL/Ubuntu-22.04.tar
 wsl --unregister Ubuntu-22.04

# wsl --import <DistributionName>  <安装位置>  <tar文件名>
 wsl --import Ubuntu-22.04 D:/WSL/Ubuntu D:/WSL/Ubuntu-22.04.tar --version 2
```

## Rocky Linux
> https://docs.rockylinux.org/zh/guides/interoperability/import_rocky_to_wsl/

1.下载对应的tar.xz文件，解压为tar文件。
2.执行下列命令进行导入：
```bash
# wsl --import rocky-9 <path-to-vm-dir> <path-to/rocky-9-image.tar.xz> --version 2
wsl --import rocky-9 D:/WSL/rocky D:/WSL/Rocky-9-Container-Base.latest.x86_64.tar --version 2
```



