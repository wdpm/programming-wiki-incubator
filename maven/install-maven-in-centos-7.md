# install maven in centos 7

## by yum repo
```bash
yum install maven -y
```
it will update java version to openjdk. if you don't want to use openjdk,you must re-config java version.

check maven version
```bash
[root@vmware0 share]# mvn -v
Apache Maven 3.0.5 (Red Hat 3.0.5-17)
Maven home: /usr/share/maven
Java version: 1.8.0_232, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "3.10.0-862.el7.x86_64", arch: "amd64", family: "unix"
```

## by tar.gz file

> todo

## configure maven settings

use ``mvn -X`` to find where settings locate.
```bash
[root@vmware0 repository]# mvn -X
Apache Maven 3.0.5 (Red Hat 3.0.5-17)
Maven home: /usr/share/maven
...
[DEBUG] Reading global settings from /usr/share/maven/conf/settings.xml
[DEBUG] Reading user settings from /root/.m2/settings.xml
[DEBUG] Using local repository at /root/.m2/repository
```
```bash
nano /usr/share/maven/conf/settings.xml
```
```
<!--
 | This is the configuration file for Maven. It can be specified at two levels:
 |
 |  1. User Level. This settings.xml file provides configuration for a single user,
 |                 and is normally provided in ${user.home}/.m2/settings.xml.
 |
 |                 NOTE: This location can be overridden with the CLI option:
 |
 |                 -s /path/to/user/settings.xml
 |
 |  2. Global Level. This settings.xml file provides configuration for all Maven
 |                 users on a machine (assuming they're all using the same Maven
 |                 installation). It's normally provided in
 |                 ${maven.home}/conf/settings.xml.
 |
 |                 NOTE: This location can be overridden with the CLI option:
 |
 |                 -gs /path/to/global/settings.xml
 |-->
```
In summary:
- User Level: ``${user.home}/.m2/settings.xml``
- Global Level: ``${maven.home}/conf/settings.xml``

So we can edit user level settings:
```bash
nano /root/.m2/settings.xml
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
    <mirrors>
        <mirror>
            <id>nexus-aliyun</id>
            <mirrorOf>*</mirrorOf>
            <name>Nexus aliyun</name>
            <url>http://maven.aliyun.com/nexus/content/groups/public</url>
        </mirror>
    </mirrors>
</settings>
```