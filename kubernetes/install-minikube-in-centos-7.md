# install minikube in centos 7

## 安装 docker

参考其他文档。

## 关闭 swap

```
swapoff -a
```

如果想要永久关闭swap，可以`nano /etc/fstab`将swap的行注释掉。

## 配置 kubernetes yum repo

```bash
cat > /etc/yum.repos.d/kubernetes.repo <<EOF 
[kubernetes] 
name=Kubernetes 
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64 
enabled=1 
gpgcheck=0 
repo_gpgcheck=0 
EOF
```

## 安装 kubectl

访问 https://storage.googleapis.com/kubernetes-release/release/stable.txt获取最新版本号，例如 v1.16.3。

```bash
curl -LO https://storage.googleapis.com/kubernetes-release/release/v1.16.3/bin/linux/amd64/kubectl

cp kubectl /usr/local/bin/
chmod +x /usr/local/bin/kubectl

[root@vmware0 ~]# kubectl version
Client Version: version.Info{Major:"1", Minor:"16", GitVersion:"v1.16.3", GitCommit:"b3cbbae08ec52a7fc73d334838e18d17e8512749", GitTreeState:"clean", BuildDate:"2019-11-13T11:23:11Z", GoVersion:"go1.12.12", Compiler:"gc", Platform:"linux/amd64"}
Server Version: version.Info{Major:"1", Minor:"15", GitVersion:"v1.15.0", GitCommit:"e8462b5b5dc2584fdcd18e6bcfe9f1e4d970a529", GitTreeState:"clean", BuildDate:"2019-06-19T16:32:14Z", GoVersion:"go1.12.5", Compiler:"gc", Platform:"linux/amd64"}

[root@vmware0 ~]# kubectl cluster-info
Kubernetes master is running at https://192.168.31.12:8443
KubeDNS is running at https://192.168.31.12:8443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'
```

可以安装 bash-completion 增强 kubectl 的补全功能

```bash
yum install bash-completion
source <(kubectl completion bash)
```

## 安装 minikube

阿里云发布的minikube地址: https://github.com/AliyunContainerService/minikube

```bash
curl -Lo minikube http://kubernetes.oss-cn-hangzhou.aliyuncs.com/minikube/releases/v1.2.0/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

# config default vm-driver none or vmware
sudo minikube config set vm-driver none

[root@vmware0 ~]# minikube start --registry-mirror=https://registry.docker-cn.com
* minikube v1.2.0 on linux (amd64)
* using image repository registry.cn-hangzhou.aliyuncs.com/google_containers
* Creating none VM (CPUs=2, Memory=2048MB, Disk=20000MB) ...
* Configuring environment for Kubernetes v1.15.0 on Docker 19.03.5
* Downloading kubeadm v1.15.0
* Downloading kubelet v1.15.0
* Pulling images ...
* Launching Kubernetes ... 
* Configuring local host environment ...

! The 'none' driver provides limited isolation and may reduce system security and reliability.
! For more information, see:
  - https://github.com/kubernetes/minikube/blob/master/docs/vmdriver-none.md

! kubectl and minikube configuration will be stored in /root
! To use kubectl or minikube commands as your own user, you may
! need to relocate them. For example, to overwrite your own settings:

  - sudo mv /root/.kube /root/.minikube $HOME
  - sudo chown -R $USER $HOME/.kube $HOME/.minikube

* This can also be done automatically by setting the env var CHANGE_MINIKUBE_NONE_USER=true
* Verifying: apiserver proxy etcd scheduler controller dns
* Done! kubectl is now configured to use "minikube"

[root@vmware0 ~]# minikube status
host: Running
kubelet: Running
apiserver: Running
kubectl: Correctly Configured: pointing to minikube-vm at 192.168.31.12

[root@vmware0 ~]# kubectl get node
NAME       STATUS   ROLES    AGE   VERSION
minikube   Ready    master   13h   v1.15.0
```

## 完全卸载minikube
```bash
minikube stop; minikube delete &&
docker stop $(docker ps -aq) &&
rm -rf ~/.kube ~/.minikube &&
sudo rm -rf /usr/local/bin/localkube /usr/local/bin/minikube &&
launchctl stop '*kubelet*.mount' &&
launchctl stop localkube.service &&
launchctl disable localkube.service &&
sudo rm -rf /etc/kubernetes/ &&
docker system prune -af --volumes
```

## 参考文档

- [1] https://kubernetes.io/docs/tasks/tools/install-kubectl/#verifying-kubectl-configuration 
- [2] https://minikube.sigs.k8s.io/docs/start/linux/ 
- [3] https://github.com/kubernetes/minikube/issues/5860 
