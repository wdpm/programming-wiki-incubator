# k8s 应用扩展

## 自定义API对象

### 创建CRD对象

`website-crd.yaml`

```yml
apiVersion: apiextensions.k8s.io/v1beta1
kind: CustomResourceDefinition
metadata:
  name: websites.extensions.example.com
spec:
  scope: Namespaced #命名空间
  group: extensions.example.com #组名
  version: v1 #版本
  names:
    kind: Website #类型
    singular: website
    plural: websites
```

```bash
[root@k8s-master C18]# k create -f website-crd.yaml 
customresourcedefinition.apiextensions.k8s.io/websites.extensions.example.com created
[root@k8s-master C18]# k get crd
NAME                              CREATED AT
websites.extensions.example.com   2019-12-19T01:14:59
```

在创建自定义网站资源的实例时， apiVersion 属性需要设置为extensions.example.com/v1。

### 创建自定义资源实例

`kubia-website.yaml`

```yml
apiVersion: extensions.example.com/v1
kind: Website
metadata:
  name: kubia
spec:
  gitRepo: https://github.com/luksa/kubia-website-example.git
```

```bash
[root@k8s-master C18]# k create -f kubia-website.yaml
[root@k8s-master C18]# k get websites
NAME    AGE
kubia   53s
```

```bash
[root@k8s-master C18]# k describe website kubia
Name:         kubia
Namespace:    default
Labels:       <none>
Annotations:  <none>
API Version:  extensions.example.com/v1
Kind:         Website
Metadata:
  Creation Timestamp:  2019-12-19T01:18:19Z
  Generation:          1
  Resource Version:    1232680
  Self Link:           /apis/extensions.example.com/v1/namespaces/default/websites/kubia
  UID:                 ebe6d781-141c-4b3e-9287-7f0223e16f54
Spec:
  Git Repo:  https://github.com/luksa/kubia-website-example.git
Events:      <none>
```

```bash
[root@k8s-master C18]# k delete website kubia
website.extensions.example.com "kubia" deleted
```

## 服务目录简介

服务目录就是列出所有服务的目录。

服务代理让你能够在Kubemetes 中轻松配置和暴露服务。

服务目录不会为每种服务类型的API 服务器添加自定义资源， 而是将以下四种通用API资源引入其中：

1. 一个ClusterServiceBroker, 描述一个可以提供服务的（外部）系统
2. 一个ClusterServiceClass, 描述一个可供应的服务类型
   
3. 一个Servicelnstance, 已配置服务的一个实例
   
4. 一个ServiceBinding, 表示一组客户端(pod)和Servicelnstance之间的绑定

![](assets/service-catalog.PNG)



## 基于K8s搭建的平台

### OpenShift

- 多用户环境。
- 清单可参数化来部署资源，这个可参数化的列表就被称为模板。本质上，模板就是一个包含了参数列表的JSON 或YAML 文件。
- 使用构建配置通过源码构建镜像。
- 使用部署配置自动部署新建的镜像。
- 使用路由向外部暴露服务。
- 试用MiniShift（与Minikube 等效的
  OpenShift）

### Deis Workflow + Helm

- 运行
  Workflow 时， 会创建一组Service 和ReplicationController, 这会为开发人员提供一个简单、友好的开发环境。
- Helm 是一个K8s 包管理器。由helm cli和Tiller组成。Helm 图表非常实用。

