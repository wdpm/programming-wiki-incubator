# install jenkins on centos 7

## install
> https://pkg.jenkins.io/redhat/
```bash
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat/jenkins.io.key
yum install jenkins -y
```

## configure
change JENKINS_PORT to 8888
```bash
nano /etc/sysconfig/jenkins
JENKINS_PORT="8888"
```
start jenkins 
```bash
service jenkins start
```
browser visit ``http://IP:8888/``

1.Unlock Jenkins
```bash
cat /var/lib/jenkins/secrets/initialAdminPassword
```
you will get admin password.

2.Customize Jenkins

install suggested plugins.Sometime plugins will be installed fail due to network problem.

3.Create First Admin User
```
Username: admin
Password: admin
Confirm password: admin
Full name: admin
E-mail address: admin@example.com
```

4.Instance Configuration

Jenkins URL: http://192.168.31.12:8888/

then browser visit ``http://192.168.31.12:8888/``