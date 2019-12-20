# k8s cluster

## 主节点

禁用selinux

```bash
nano /etc/selinux/config
SELINUX=disabled
```

```
reboot
```

禁用防火墙

```bash
systemctl disable firewalld && systemctl stop firewalld
```

Yum仓库添加k8s RPM

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

安装 Docker、kubelet、kubeadm、kubectl、kubernetes-CNI

```bash
yum install -y docker kubelet kubeadm kubectl kubernetes-cni
```

```
docker          2:1.13.1-103.git7f2769b.el7.centos   
kubeadm         1.17.0-0                              
kubectl         1.17.0-0                             
kubelet         1.17.0-0                           
kubernetes-cni  0.7.5-0             
```

手动启动docker 和kubelet 服务

```bash
systemctl enable docker && systemctl start docker
systemctl enable kubelet && systemctl start kubelet
```

启用 bridge-nf-call-iptables 内核选项

```bash
sysctl -w net.bridge.bridge-nf-call-iptables=1
echo "net.bridge.bridge-nf-call-iptables=1" > /etc/sysctl.d/k8s.conf
```

禁用交换分区

```bash
swapoff -a && sed -i '/ swap / s/^/#/' /etc/fstab
```

`/ swap / s/^/#/`作用是在含有swap行的首行添加#注释。

关机

```bash
shutdown
```

注意，该节点的IP为

```bash
192.168.31.30 master.k8s
```

## 克隆主节点两次作为两个node

利用VMware工具的克隆，得到node1.k8s和node2.k8s，IP分别为

```bash
192.168.31.31 node1.k8s
192.168.64.32 node2.k8s
```

在三个节点中分别配置hostname

```bash
hostnamectl --static set-hostname master.k8s
hostnamectl --static set-hostname node1.k8s
hostnamectl --static set-hostname node2.k8s
```

在三个节点中分别配置`/etc/hosts`

```bash
192.168.31.30 master.k8s
192.168.31.31 node1.k8s
192.168.64.32 node2.k8s
```

## 初始化主节点

```bash
[root@minimal log]# kubeadm init --image-repository registry.aliyuncs.com/google_containers 
...
[init] Using Kubernetes version: v1.17.0
...
Your Kubernetes control-plane has initialized successfully!
To start using your cluster, you need to run the following as a regular user:

  mkdir -p $HOME/.kube
  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
  sudo chown $(id -u):$(id -g) $HOME/.kube/config

You should now deploy a pod network to the cluster.
Run "kubectl apply -f [podnetwork].yaml" with one of the options listed at:
  https://kubernetes.io/docs/concepts/cluster-administration/addons/

Then you can join any number of worker nodes by running the following on each as root:

kubeadm join 192.168.31.30:6443 --token 6wqhe5.qlm7xv6lkt5upeno \
    --discovery-token-ca-cert-hash sha256:531350ac02e928a2cf22dfde3defd83e5929f0d61219a0a985cc3eb9db6d3e18 
```
```bash
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```
> 这步保证在主节点 kubectl 可以跟 cluster 通信。

Kubelet会监控目录`/etc/kubernetes/manifests`，并通过Docker运行这些组件

```bash
[root@master ~]# cd /etc/kubernetes/manifests
[root@master manifests]# ll
total 16
-rw------- 1 root root 1791 Dec 20 00:49 etcd.yaml
-rw------- 1 root root 2635 Dec 20 00:49 kube-apiserver.yaml
-rw------- 1 root root 2417 Dec 20 00:49 kube-controller-manager.yaml
-rw------- 1 root root 1149 Dec 20 00:49 kube-scheduler.yaml
```

`/etc/kubemetes/admin.conf`文件展示了必要的一些配置。只需通过设置KUBECONFIG环境变量让kubectl使用它。

```bash
export KUBECONFIG=/etc/kubernetes/admin.conf
```

检查kubectl是否正常

```bash
[root@master kubernetes]# kubectl get po -n kube-system
NAME                                 READY   STATUS    RESTARTS   AGE
coredns-9d85f5447-2j9ll              0/1     Pending   0          7m15s
coredns-9d85f5447-fstmg              0/1     Pending   0          7m15s
etcd-master.k8s                      1/1     Running   0          7m3s
kube-apiserver-master.k8s            1/1     Running   0          7m3s
kube-controller-manager-master.k8s   1/1     Running   0          7m3s
kube-proxy-g6vnx                     1/1     Running   0          7m15s
kube-scheduler-master.k8s            1/1     Running   0          7m3s
```

coredns目前为Pending。

```bash
[root@master kubernetes]# k get node
NAME         STATUS     ROLES    AGE   VERSION
master.k8s   NotReady   master   10m   v1.17.0
```

主节点的状态显示为NotReady。

## 配置工作节点

分别在两个工作节点运行：

```bash
kubeadm join 192.168.31.30:6443 --token 6wqhe5.qlm7xv6lkt5upeno \
    --discovery-token-ca-cert-hash sha256:531350ac02e928a2cf22dfde3defd83e5929f0d61219a0a985cc3eb9db6d3e18 
```

在主节点再次查看节点状态。

```bash
[root@master kubernetes]# k get node
NAME         STATUS     ROLES    AGE     VERSION
master.k8s   NotReady   master   17m     v1.17.0
node1.k8s    NotReady   <none>   4m55s   v1.17.0
node2.k8s    NotReady   <none>   4s      v1.17.0
```

他们的状态依然都是NotReady。

在主节点运行查看node1的描述

```bash
kubectl describe node nodel.k8s
```

```bash
KubeletNotReady              runtime network not ready: NetworkReady=false reason:NetworkPluginNotReady message:docker: network plugin is not ready: cni config uninitialized
```

Kubelet没有完全准备好是因为容器网络（CNI）插件没有准备好，因为你还没有部署CNI插件，下面进行部署。

## 配置容器网络

在主节点

```bash
[root@master kubernetes]# kubectl apply -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"
serviceaccount/weave-net created
clusterrole.rbac.authorization.k8s.io/weave-net created
clusterrolebinding.rbac.authorization.k8s.io/weave-net created
role.rbac.authorization.k8s.io/weave-net created
rolebinding.rbac.authorization.k8s.io/weave-net created
daemonset.apps/weave-net created
```

等待一段时间后，再次检查

```bash
[root@master .kube]# k get node
NAME         STATUS   ROLES    AGE   VERSION
master.k8s   Ready    master   33m   v1.17.0
node1.k8s    Ready    <none>   21m   v1.17.0
node2.k8s    Ready    <none>   16m   v1.17.0
[root@master .kube]# k get po --all-namespaces
NAMESPACE     NAME                                 READY   STATUS    RESTARTS   AGE
kube-system   coredns-9d85f5447-2j9ll              1/1     Running   0          33m
kube-system   coredns-9d85f5447-fstmg              1/1     Running   0          33m
kube-system   etcd-master.k8s                      1/1     Running   0          33m
kube-system   kube-apiserver-master.k8s            1/1     Running   0          33m
kube-system   kube-controller-manager-master.k8s   1/1     Running   0          33m
kube-system   kube-proxy-c757d                     1/1     Running   0          16m
kube-system   kube-proxy-g6vnx                     1/1     Running   0          33m
kube-system   kube-proxy-jtl96                     1/1     Running   0          21m
kube-system   kube-scheduler-master.k8s            1/1     Running   0          33m
kube-system   weave-net-8jnxj                      2/2     Running   0          10m
kube-system   weave-net-prlph                      2/2     Running   0          10m
kube-system   weave-net-rcbf8                      2/2     Running   0          10m
```

一个功能齐全的三节点Kubemetes集群，并具有
Weave Net提供的覆盖网络完成。

## 配置node节点可以跟集群通信
在 node1, 将主节点的 KUBECONFIG 配置复制过来。
```bash
mkdir -p $HOME/.kube
sudo scp root@192.168.31.30:/etc/kubernetes/admin.conf ~/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
export KUBECONFIG=~/.kube/config
```
验证kubectl是否可以和进群通信
```bash
[root@node1 .kube]# kubectl get node
NAME         STATUS   ROLES    AGE   VERSION
master.k8s   Ready    master   57m   v1.17.0
node1.k8s    Ready    <none>   45m   v1.17.0
node2.k8s    Ready    <none>   40m   v1.17.0
```
node2同理。