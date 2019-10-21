# 魔灵召唤-数据导出篇（Windows + IOS）

> Author: 暴走の凌霜降 
>
> Date：2019-10-20

## 前提

- iphone（ios 12)
- PC（Windows 10），带wifi发射器，能够上网

## Setup

### 1. 下载 SWEX 0.0.27 windows版本 

   访问 [sw-exporter-setup-0.0.27.exe](https://github.com/Xzandro/sw-exporter/releases/download/0.0.27/sw-exporter-setup-0.0.27.exe)，安装于PC。

### 2. 确认PC上外网IPv4地址

   打开“网络和Internet”设置 > 更改适配器选项，选择你当前上网的适配器，右键状态：

   ![](assets\WLAN-in-PC.jpg)

   ![](assets\get-IPv4-address.jpg)

   点击详细信息，获取IPv4地址。图中示例为192.168.31.170。

### 3. 打开SWEX并配置

   ![](assets\config-SWEX.jpg)

   ①：选择上一步获取到的IPv4地址，这里是192.168.31.170。

   ②：端口保持8080不变。

   ③：点击Get Cert。会看见一句提示：

   ```bash
   C:\Users\XXX\Desktop\Summoners War Exporter Files\cert\ca.pem.
   ```

   前往PC中该路径可以看到该证书文件 ca.pem。将该文件通过QQ邮箱的附件方式发送到自己的邮箱。

   前往QQ邮箱主页 > 设置 > POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务 中开启IMAP/SMTP服务：

   ![](assets\enable-IMAP-SMTP-service-in-QQ-email.jpg)

   在iphone中绑定QQ邮箱：设置 > 账户与密码 > 添加账户。

   在iphone自带的邮件App登录QQ邮箱，打开包含 ca.pem 附件的邮件，点击ca.pem，安装。

   在iphone中信任该证书文件：设置 > 关于本机 > 证书信任设置 > 开启证书信任。

   ④：点击启动代理。

### 4. 在PC中开启无线热点wifi，并转到设置

   ![](assets\start-wifi-hotspot-in-PC.jpg)

   记住网络名称和设置wifi密码。

### 5. 在iphone连接PC的wifi热点

![](assets\connect-to-your-wifi-proxy-in-iphone.png)

### 6. 启动魔灵召唤

如果不出意外的话，SWEX中会有一局提示:

![](assets\get-profile-json.jpg)

在PC的 C:\Users\XXX\Desktop\Summoners War Exporter Files 路径下既可以找到该文件。

### 7. 下一步

魔灵召唤-数据导出篇到此结束，后续是魔灵召唤-符文优化篇。

