# LivenessProbe

liveness probe，用于检测容器是否还在运行。三种机制：

- HTTP GET：尝试发送HTTP请求
- TCP Socket：尝试与容器指定端口简历TCP连接
- Exec：尝试在容器内执行命令

使用存活探针：

构建一个会发生500错误响应的node.js app

```js
const http = require('http');
const os = require('os');

console.log("Kubia server starting...");

var requestCount = 0;

var handler = function(request, response) {
  console.log("Received request from " + request.connection.remoteAddress);
  requestCount++;
  if (requestCount > 5) {
    response.writeHead(500);
    response.end("I'm not well. Please restart me!");
    return;
  }
  response.writeHead(200);
  response.end("You've hit " + os.hostname() + "\n");
};

var www = http.createServer(handler);
www.listen(8080);
```

dockerfile:

```bash
FROM node:7
ADD app.js /app.js
ENTRYPOINT ["node", "app.js"]
```

将该应用构建成image：

```bash
docker build -t luksa/kubia-unhealthy .
```

在pod配置中启用livenessProbe的httpget方式：

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: kubia-liveness
spec:
  containers:
  - image: luksa/kubia-unhealthy
    name: kubia
    livenessProbe:
      httpGet:
        path: /
        port: 8080
```

```bash
[root@vmware0 Chapter04]# kubectl create -f kubia-liveness-probe.yaml
[root@vmware0 Chapter04]# kubectl get po
NAME             READY   STATUS              RESTARTS   AGE
kubia-liveness   0/1     ContainerCreating   0          5s
[root@vmware0 Chapter04]# kubectl logs kubia-liveness -f
Kubia server starting...
Received request from ::ffff:172.17.0.1
Received request from ::ffff:172.17.0.1
Received request from ::ffff:172.17.0.1
Received request from ::ffff:172.17.0.1
Received request from ::ffff:172.17.0.1
Received request from ::ffff:172.17.0.1
Received request from ::ffff:172.17.0.1
Received request from ::ffff:172.17.0.1
[root@vmware0 grub]# kubectl get po
NAME             READY   STATUS    RESTARTS   AGE
kubia-liveness   1/1     Running   1          2m19s
[root@vmware0 grub]# kubectl describe po kubia-liveness
...
Restart Count:  2
Liveness:  http-get http://:8080/ delay=0s timeout=1s period=10s #success=1 #failure=3
Environment:  <none>
...
Warning  Unhealthy  2m10s (x9 over 6m10s)  kubelet, minikube  Liveness probe failed: HTTP probe failed with statuscode: 500
Normal   Killing    2m10s (x3 over 5m50s)  kubelet, minikube  Container kubia failed liveness probe, will be restarted
...   
```

delay=0s timeout=1s period=10s 表示：

- delay=0s，部分显示在容器启动后立即开始探测。
- timeout=1s，容器必须在1秒内进行响应，否则这次探测记作失败。
- period=10s，每10秒探测一次容器, 并在探测连续三次失败后重启容器。

理论上，应该设置合理的初始探测延迟，即initialDelaySeconds: 15。

重启容器这项工作是由kubelet完成的。

