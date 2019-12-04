# CronJob

在未来计划的时间内，CronJob资源会创建Job资源，然后Job创建pod。

``cronjob.yaml``

```yaml
apiVersion: batch/v1beta1
kind: CronJob
metadata:
  name: batch-job-every-fifteen-minutes
spec:
  schedule: "0,15,30,45 * * * *" # minute,hour,day,month,dayOfWeek
  jobTemplate:
    spec:
      template:
        metadata:
          labels:
            app: periodic-batch-job
        spec:
          restartPolicy: OnFailure
          containers:
          - name: main
            image: luksa/batch-job
```

建议CronJob的任务是幂等的，否则可能会发生意外结果。

