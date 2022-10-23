# Crack navicat premium 12
> https://github.com/DoubleLabyrinth/navicat-keygen/blob/windows/README_FOR_WINDOWS.zh-CN.md

假设navicat premium 12安装路径为:
```
C:\Program Files\PremiumSoft\Navicat Premium 12
```
注册机解压路径为:
```
C:\Program Files\PremiumSoft\navicat-keygen-for-x64
```
该目录下含有:
- navicat-keygen.exe
- navicat-patcher.exe

> 以**管理员身份**打开CMD

1. 使用navicat-patcher.exe替换掉navicat.exe和libcc.dll里的Navicat激活公钥:
```bash
cd C:\Program Files\PremiumSoft\navicat-keygen-for-x64
navicat-patcher.exe "C:\Program Files\PremiumSoft\Navicat Premium 12"
```
```
***************************************************
*       Navicat Patcher by @DoubleLabyrinth       *
*           Release date: Jan 19 2019             *
***************************************************

Press Enter to continue or Ctrl + C to abort.
```

2. 使用navicat-keygen.exe来生成序列号和激活码:
```bash
C:\Program Files\PremiumSoft\navicat-keygen-for-x64>navicat-keygen.exe -text .\RegPrivateKey.pem
Select Navicat product:
0. DataModeler
1. Premium
2. MySQL
3. PostgreSQL
4. Oracle
5. SQLServer
6. SQLite
7. MariaDB
8. MongoDB
9. ReportViewer

(Input index)> 1

Select product language:
0. English
1. Simplified Chinese
2. Traditional Chinese
3. Japanese
4. Polish
5. Spanish
6. French
7. German
8. Korean
9. Russian
10. Portuguese

(Input index)> 1

(Input major version number, range: 0 ~ 15, default: 12)> 12

Serial number:
NAVM-PO6Z-EYMM-YHDM

Your name: testUser
Your organization: TestOrg

Input request code (in Base64), input empty line to end:
```
不要关闭命令行

3. 断开网络，并打开navicat。注册 -> 填入keygen生成的序列号 -> 点击激活 -> 手动激活
4. 将手动激活的请求码复制到第2步 CMD 中，并按两下Enter键结束输入。
```
SSbl8TwmVXjfK8+tpjB9HyY0U7q5GrJNINizQWHteCTDxE8jSKq/ZT/OImuaoSK2aZyjdP15kyx0tPQfDRMppThySQDQwfFU3GtbWDtf27AreCu8tu8rHUghn9TYwOZbmPEDPXytBAgXKp2oH5B4LPmJP5Z/Iu1Tl3h/PMorAJIpUUHZyAofZxuehYhoZ3XJ+kUG3dYcY1RIA8730qvmAA0zoglHEuVg0dtMzqaWokwyuQWig+nevjLMwEXWvXqn0uFaWPYtIDBsi1vV8gBoqtEjReB8He3/v7Gs9xX/gU0BxujNWi0D8bRm4oycLqTTJEHzD1vEPVWdWgqPeLevjg==

Request Info:
{"K":"NAVMPO6ZEYMMYHDM", "DI":"WcNDsRnv8LBmspTakqzS", "P":"WIN"}

Response Info:
{"K":"NAVMPO6ZEYMMYHDM","DI":"WcNDsRnv8LBmspTakqzS","N":"testUser","O":"TestOrg","T":1562225057}

License:
kWE/LSBKc1nhD6/vH0yFgv8zUr0iO2Og2+PmY4fGuiW6m85pLEWz9Kt5r2fvArcs
3ZlRwzYlT4nZ0jBhjQ1WRpQQHR13ag6/kg5nC77+nX1/LYP/74eBT51nANf/dBRT
gIUaTSffJSKJwBudXUWZQhYuF+FUs+OAkpYMI2MZTtzH+vJGPbNxJv5dUjxm0RAz
RLwxAxI2Ft/HO46kif3Lh1UzZLdUppp1I9XiS7BMDklvaHg9cI+9XPrDFyChjXtL
4r1yigUfUOtS6y3rDKcoQTWeRoPt3Zvv9kYy2bj3tNNtgJ9MB5ay4WYp8u/kliMd
zW5A9z5hZZOnWcLuk+sl6A==
```
5. 将 License 填入 navicat 即可。