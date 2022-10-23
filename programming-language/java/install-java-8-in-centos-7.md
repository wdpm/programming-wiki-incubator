# Install Java 8 in CentOS 7
download one rpm file from official website:
```
jdk-8u211-linux-x64.rpm
```
> Ref: https://www.oracle.com/technetwork/java/javase/downloads/index.html

## Install
After uploading to your server:
```bash
rpm -Uvh jdk-8u211-linux-x64.rpm
```
If you has OpenJDK before，you can switch java version by:
``` bash
# alternatives --config java
There are 2 programs which provide 'java'.

  Selection    Command
-----------------------------------------------
*+ 1           java-1.8.0-openjdk.x86_64 (/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.212.b04-0.el7_6.x86_64/jre/bin/java)
   2           /usr/java/jdk1.8.0_211-amd64/jre/bin/java

Enter to keep the current selection[+], or type selection number: 2

# java -version
java version "1.8.0_211"
Java(TM) SE Runtime Environment (build 1.8.0_211-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.211-b12, mixed mode)
```

## Config JAVA_HOME
```bash
# for all user in this computer
nano /etc/profile
export JAVA_HOME=/usr/java/jdk1.8.0_211-amd64
#export JRE_HOME=${JAVA_HOME}/jre
#export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=$JAVA_HOME/bin:$PATH
```
Check if work as well:
```
# source /etc/profile
# echo $JAVA_HOME
/usr/java/jdk1.8.0_211-amd64
```
> Ref: https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/