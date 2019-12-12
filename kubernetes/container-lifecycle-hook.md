# 容器生命周期钩子

容器生命周期钩子有两种：启动后钩子，停止前钩子。与探针类似，生命周期钩子可以在容器内部执行一个命令
，或向一个URL发送HTTP GET请求。

## 启动后容器生命周期钩子

`post-start-hook-exec.yaml`

```yml
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-poststart-hook-exec
spec:
  containers:
  - image: luksa/kubia
    name: kubia
    lifecycle:
      postStart:
        exec:
          command: 
          - sh
          - -c
          - "echo 'hook will fail with exit code 15'; sleep 5 ; exit 15"
```

`post-start-hook-httpget.yaml`

```yml
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-poststart-hook-httpget
spec:
  containers:
  - image: luksa/kubia
    name: kubia
    ports:
    - containerPort: 8080
      protocol: TCP
    lifecycle:
      postStart:
        httpGet:
          port: 9090
          path: postStart
```

## 停止前容器生命周期钩子

`pre-stop-hook-httpget.yaml`

```yml
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-prestop-hook
spec:
  containers:
  - image: luksa/kubia
    name: kubia
    ports:
    - containerPort: 8080
      protocol: TCP
    lifecycle:
      preStop:
        httpGet:
          port: 8080
          path: shutdown
```

关于钩子的调试。基于命令的启动后**钩子输出到标准输出终端和错误输出终端的内容在任何地方都不会记录**。

这种情况下， 可以通过给容器挂载一个emptyDir卷，
并且让钩子程序向这个存储卷写入内容来解决。

`pre-stop-hook-volume.yaml`

```yml
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-prestop-hook
spec:
  containers:
  - image: luksa/kubia
    name: kubia
    command:
    - sh
    - -c
    - 'echo "`date` start" >> /tmp/log/log.txt ; sleep 10000 ; exit 1'
    livenessProbe:
      initialDelaySeconds: 30
      exec:
        command:
        - sh
        - -c
        - "exit 1"
    volumeMounts:
    - name: log
      mountPath: /tmp/log
    lifecycle:
      preStop:
        exec:
          command: 
          - sh
          - -c
          - 'echo "`date` preStop" >> /tmp/log/log.txt'
  volumes:
  - name: log
    emptyDir:
      medium: Memory
```

```bash
[root@vmware0 chapter17]# k create -f pre-stop-hook-volume.yaml
pod/pod-with-prestop-hook created
[root@vmware0 chapter17]# k exec pod-with-prestop-hook -- cat /tmp/log/log.txt
Thu Dec 12 19:09:57 UTC 2019 start
Thu Dec 12 19:10:53 UTC 2019 preStop
Thu Dec 12 19:11:27 UTC 2019 start
Thu Dec 12 19:12:23 UTC 2019 preStop
```

这个pod会不断重启，测试完毕就删除这个pod即可。