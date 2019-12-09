# volume

<img src="assets/3-containers-with-2-volumes-in-1-pod.png" style="zoom: 50%;" />

## volum生命周期

卷的生存周期与pod 的生存周期相关联，当删除pod 时，
卷的内容就会丢失。

## volume类型

- emptyDir： 临时数据的空目录
- hostPath： 工作节点的文件系统挂载到pod
- gitRepo： git仓库内容初始化卷
- nfs：NFS共享卷
- configMap, secret, downwardAPI
- persistentVolumeClaim

### emptyDir

build a fortune docker image:

``fortuneloop.sh``

```bash
#!/bin/bash
# SIGINT = ctrl + c, call exit
trap "exit" SIGINT
mkdir /var/htdocs

while :
do
  echo $(date) Writing fortune to /var/htdocs/index.html
  /usr/games/fortune > /var/htdocs/index.html
  sleep 10
done
```

``dockerfile``

```dockerfile
FROM ubuntu:latest
RUN apt-get update ; apt-get -y install fortune
ADD fortuneloop.sh /bin/fortuneloop.sh
ENTRYPOINT /bin/fortuneloop.sh
```

```bash
docker build -t luksa/fortune .
docker push luksa/fortune
```

``fortune-pod.yml``

```yml
apiVersion: v1
kind: Pod
metadata:
  name: fortune
spec:
  containers:
  - image: luksa/fortune
    name: html-generator
    volumeMounts:
    - name: html
      mountPath: /var/htdocs
  - image: nginx:alpine
    name: web-server
    volumeMounts:
    - name: html
      mountPath: /usr/share/nginx/html
      readOnly: true
    ports:
    - containerPort: 80
      protocol: TCP
  volumes:
  - name: html
    emptyDir: {}
      # medium: Memory
```

```bash
kubectl port-forward fortune 8080:80
# new a terminal
curl http://localhost:8080
```

