# run github actions locally by act

## installation
```bash
cd ~
wget https://github.com/nektos/act/releases/download/v0.2.40/act_Linux_x86_64.tar.gz
mkdir act && tar -zxvf act_Linux_x86_64.tar.gz -C act
cp act/act /usr/local/bin
act --version
```
然后拉取sample project进行测试。
```bash
git clone https://github.com/cplee/github-actions-demo
cd github-actions-demo
```

```
act -l
act -g
# dry-run
act -n
# verbose & dry-run
act -vn
# verbose
act -v
```
在 dry-run 这一步遇到了下面的错误。
```bash
root@DESKTOP-QLDBOG2:~/github-actions-demo# act -n
*DRYRUN* [CI/test] 🚀  Start image=catthehacker/ubuntu:act-latest
*DRYRUN* [CI/test]   🐳  docker pull image=catthehacker/ubuntu:act-latest platform= username= forcePull=false
*DRYRUN* [CI/test]   🐳  docker create image=catthehacker/ubuntu:act-latest platform= entrypoint=["tail" "-f" "/dev/null"] cmd=[]
*DRYRUN* [CI/test]   🐳  docker run image=catthehacker/ubuntu:act-latest platform= entrypoint=["tail" "-f" "/dev/null"] cmd=[]
*DRYRUN* [CI/test]   ☁  git clone 'https://github.com/actions/setup-node' # ref=v1
*DRYRUN* [CI/test] Unable to clone https://github.com/actions/setup-node refs/heads/v1: Get "https://github.com/actions/setup-node/info/refs?service=git-upload-pack": dial tcp 52.69.186.44:443: i/o timeout
```
初步推测docker是内部网络无法访问github问题，需要处理docker内部的代理设置。

## network troubleshooting 

第一次尝试：参考[该issue](https://github.com/nektos/act/issues/1578) ，在command line 中加入代理设置：
```bash
act -vn --env http_proxy=http://192.168.32.1:7890 --env https_proxy=http://192.168.32.1:7890
act -vn --env http_proxy=http://192.168.1.6:7890 --env https_proxy=http://192.168.1.6:7890
```
结果：无效。

---

第二次尝试： 参考[这个issue](https://github.com/nektos/act/issues/1032) ，Put this in ~/.actrc.
```bash
--env http_proxy=http://192.168.32.1:7890 
--env https_proxy=http://192.168.32.1:7890
```
结果：无效。

---

为了确定本地环境的代理设置，新建一个Dockerfile进行git clone测试：
```dockerfile
FROM ubuntu
RUN apt-get update
RUN apt-get install -y git
RUN git config --global http.proxy http://192.168.1.6:7890 && git config --global https.proxy http://192.168.1.6:7890
RUN git clone https://github.com/actions/setup-node
```
上面的代理ip:port已确保正确，执行 `docker build . -t git-clone`，结果如下：

```bash
Sending build context to Docker daemon  19.92MB
Step 1/5 : FROM ubuntu
 ---> 6b7dfa7e8fdb
Step 2/5 : RUN apt-get update
 ---> Using cache
 ---> 052e408689bb
Step 3/5 : RUN apt-get install -y git
 ---> Using cache
 ---> 831b5755117b
Step 4/5 : RUN git config --global http.proxy http://192.168.1.6:7890 && git config --global https.proxy http://192.168.1.6:7890
 ---> Running in 5b39f2f39f20
Removing intermediate container 5b39f2f39f20
 ---> d932eb77d55b
Step 5/5 : RUN git clone https://github.com/actions/setup-node
 ---> Running in c8204b1f6945
Cloning into 'setup-node'...
Removing intermediate container c8204b1f6945
 ---> 4375046f47a0
Successfully built 4375046f47a0
Successfully tagged git-clone:latest
```
也就是说需要在docker container里面正确设置git proxy，才能克隆项目。

但是 act并没有提供设置git config的API，只能传递env变量，因此这是一个困境。