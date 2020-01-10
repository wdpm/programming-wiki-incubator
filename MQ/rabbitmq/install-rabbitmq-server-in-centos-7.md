# Install RabbitMQ Server in CentOS 7

## Install Erlang
Visit https://www.rabbitmq.com/which-erlang.html and decide a supported version:
```
RabbitMQ version: 3.7.16	
Minimum required Erlang/OTP: 20.3.x	
Maximum supported Erlang/OTP: 22.0.x
```
Here is a yum repository for erlang 22.x:
```bash
nano /etc/yum.repos.d/rabbitmq-erlang.repo
```
```
# In /etc/yum.repos.d/rabbitmq-erlang.repo
[rabbitmq-erlang]
name=rabbitmq-erlang
baseurl=https://dl.bintray.com/rabbitmq-erlang/rpm/erlang/22/el/7
gpgcheck=1
gpgkey=https://dl.bintray.com/rabbitmq/Keys/rabbitmq-release-signing-key.asc
repo_gpgcheck=0
enabled=1
```
Before installing RabbitMQ, you must install a supported version of Erlang/OTP.
```bash
yum install erlang
```
Verify if erlang is installed:
```bash
# erl -version
Erlang (SMP,ASYNC_THREADS,HIPE) (BEAM) emulator version 10.4.4
# erl
Erlang/OTP 22 [erts-10.4.4] [source] [64-bit] [smp:4:4] [ds:4:4:10] [async-threads:1] [hipe]

Eshell V10.4.4  (abort with ^G)
>
```
## Install RabbitMQ Server
> With rpm and Downloaded RPM

Import RabbitMQ signing key:
```bash
wget https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.7.16/rabbitmq-server-3.7.16-1.el7.noarch.rpm
```
Or you can download it to local, then upload to your server.
```
rpm --import https://www.rabbitmq.com/rabbitmq-release-signing-key.asc
yum install rabbitmq-server-3.7.16-1.el7.noarch.rpm
```
## Run RabbitMQ Server
```bash
# To start the daemon by default when the system boots
systemctl enable rabbitmq-server
```
```bash
# start rabbitmq-server
systemctl start rabbitmq-server
```
```bash
# check status of rabbitmq-server
systemctl status rabbitmq-server
```
```bash
# stop rabbitmq-server
systemctl stop rabbitmq-server
```
## Reference
- https://github.com/rabbitmq/erlang-rpm
- https://www.rabbitmq.com/install-rpm.html
- http://erlang.org/documentation/doc-5.7.5/doc/installation_guide/verification.html