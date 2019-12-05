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
