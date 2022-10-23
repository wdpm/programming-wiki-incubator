# k8s change resource

| 方法              | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| kubectl edit      | 使用默认编辑器打开资源配置。修改保存并退出编辑器，资源对象会被更新。例： `kubectl edit deployment kubia` |
| kubectl patch     | 修改单个资源属性。例：`kubectl patch deployment kubia -p '{"spec": {"minReadySeconds": 10}}'` |
| kubectl apply     | 通过一个完整的YAML或JSON文件，应用其中新的值来修改对象。例：`kubectl apply -f kubia-deployment-v2.yaml` |
| kubectl replace   | 将原有对象替换为YAML/JSON文件中定义的新对象。与apply命令相反，运行这个命令前要求对象必须存在，否则会打印错误。例：`kubectl replace -f kubia-deployment-v2.yaml` |
| kubectl set image | 修改Pod、ReplicationController、Deployment、DaemonSet、Job或ReplicaSet内的镜像。例：`kubectl set image deployment kubia nodejs=luksa/kubia:v2` |

