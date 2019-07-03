# use ntpdate to sync time
```bash
yum install ntpdate -y
```
- 国内
```bash
ntpdate -u 0.cn.pool.ntp.org
# or
ntpdate -u ntp1.aliyun.com
```

- 国外
```bash
ntpdate -u time.windows.com
```