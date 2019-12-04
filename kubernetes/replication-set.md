# ReplicaSet

## RS的创建

RS是RC的替代物。RS的行为和RC相似，但是标签选择的能力更强，支持复杂的标签选择。

``kubia-replicaset.yaml``

```yaml
apiVersion: apps/v1beta2
kind: ReplicaSet
metadata:
  name: kubia
spec:
  replicas: 3
  selector:
    matchLabels:
      app: kubia # 这里表示RS管理的是含app=kubia标签的pod
  template:
    metadata:
      labels:
        app: kubia # 这里表示pod的模板，标签为app=kubia
    spec:
      containers:
      - name: kubia
        image: luksa/kubia
```

## RS的查询

```bash
[root@vmware0 Chapter04]# kubectl get pods
NAME             READY   STATUS             RESTARTS   AGE
kubia-698dj      1/1     Running            0          3h26m
kubia-dxw47      1/1     Running            0          3h26m
kubia-rncr9      1/1     Running            0          3h22m
[root@vmware0 Chapter04]# kubectl get rs
NAME    DESIRED   CURRENT   READY   AGE
kubia   3         3         3       27s
```

```bash
[root@vmware0 Chapter04]# kubectl describe rs
Name:         kubia
Namespace:    default
Selector:     app=kubia
Labels:       <none>
Annotations:  <none>
Replicas:     3 current / 3 desired
Pods Status:  3 Running / 0 Waiting / 0 Succeeded / 0 Failed
Pod Template:
  Labels:  app=kubia
  Containers:
   kubia:
    Image:        luksa/kubia
    Port:         <none>
    Host Port:    <none>
    Environment:  <none>
    Mounts:       <none>
  Volumes:        <none>
Events:           <none>
```

强大的标签选择表达式：

``kubia-replicaset-matchexpressions.yaml``

```yaml
  selector:
    matchExpressions:
      - key: app
        operator: In
        values:
         - kubia
```

## RS的删除

```bash
kubectl delete rs kubia
```
