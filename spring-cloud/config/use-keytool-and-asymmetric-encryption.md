# Use keytool and asymmetric encryption

- create jks file
```bash
$ keytool -genkeypair -alias serverkey -keyalg RSA -keystore H:/server.jks
输入密钥库口令:  111111
再次输入新口令: 111111
您的名字与姓氏是什么?
  [Unknown]:  wdpm
您的组织单位名称是什么?
  [Unknown]:  ORG
您的组织名称是什么?
  [Unknown]:  wdpm.com
您所在的城市或区域名称是什么?
  [Unknown]:  GZ
您所在的省/市/自治区名称是什么?
  [Unknown]:  GD
该单位的双字母国家/地区代码是什么?
  [Unknown]:  CN
CN=wdpm, OU=ORG, O=wdpm.com, L=GZ, ST=GD, C=CN是否正确?
  [否]:  y

输入 <serverkey> 的密钥口令
        (如果和密钥库口令相同, 按回车):  111111
再次输入新口令: 111111

Warning:
JKS 密钥库使用专用格式。建议使用 
"keytool -importkeystore -srckeystore H:/server.jks -destkeystore H:/server.jks -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。
```
- copy server.jks to ``resources/``.
- update ``bootstrap.yml``
```yaml
encrypt:
  keyStore:
    location: classpath:server.jks
    password: 111111
    alias: serverkey
    secret: 111111
```
- update pom.yml of config module.
```xml
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
        <excludes>
            <exclude>**/*.jks</exclude>
        </excludes>
    </resource>
    <!-- only copy *.jks file-->
    <resource>
        <directory>src/main/resources</directory>
        <filtering>false</filtering>
        <includes>
            <include>**/*.jks</include>
        </includes>
    </resource>
</resources>
```
- restart config server. 

You can check these endpoints by http GUI tools(such as postman):
> note you should set Authorization admin/admin

- POST http://localhost:8102/encrypt
```
body: test
result: AQAf1xVyu1Lc7tMiVlUI96A5vVY/QFr+A4+frbXNL44jI56bkMiFvUzQZWbjhcMdgqNE7oYh2o+2uo1Au6kfonP7qFmhuJ/6OCnyrJ5GeRDba5aw
yVjZIe2PqMdznxmOJPpiGeMtV+iEty6mLvYHaHqNd/oe2uR+JCLyIInTxLzNbxdMqLgUhS8e3598JUNkzyF/LPlmAtQdqtJgweMCRUQBiaj66RvLlJqfroMp
azYcFuaYc2XrfUcve1iYVXdMlHz+xJ+dmZADVxucK3FM/lxbNA0qv6tQt4xVBn4Ja3yU0AxFVLEhjdmF005E3bLfhERmhh2lCoZuOYOL6lWpkiZMaSdGb8BW
2OKXIbeZugxnFexcgBrushmylEDwW5SOExc=
```


- POST http://localhost:8102/decrypt
```
body: AQAf1xVyu1Lc7tMiVlUI96A5vVY/QFr+A4+frbXNL44jI56bkMiFvUzQZWbjhcMdgqNE7oYh2o+2uo1Au6kfonP7qFmhuJ/6OCnyrJ5GeRDba5aw
yVjZIe2PqMdznxmOJPpiGeMtV+iEty6mLvYHaHqNd/oe2uR+JCLyIInTxLzNbxdMqLgUhS8e3598JUNkzyF/LPlmAtQdqtJgweMCRUQBiaj66RvLlJqfroMp
azYcFuaYc2XrfUcve1iYVXdMlHz+xJ+dmZADVxucK3FM/lxbNA0qv6tQt4xVBn4Ja3yU0AxFVLEhjdmF005E3bLfhERmhh2lCoZuOYOL6lWpkiZMaSdGb8BW
2OKXIbeZugxnFexcgBrushmylEDwW5SOExc=
result: test
```
