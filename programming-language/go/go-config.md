# Go 配置

## import path

举例说明：假设 GOPATH=/path/to/golang，那么本地目录结构:

```
/path/to/golang/src/               <------我的所有 go 代码的根路径, GOPATH
    github.com/organization1/repo  <------依赖库1
    github.com/organization2/repo  <------依赖库1
    github.com/foo/hello           <------自己的代码       
```