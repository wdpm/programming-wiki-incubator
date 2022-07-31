# the design of web apis

## REST API and HTTP Method Cheat Sheet

![image-20220705113059323](assets/image-20220705113059323.png)



## Designing the API’s data

![image-20220705113536249](assets/image-20220705113536249.png)

这里列出了四个关键的步骤:

- concept design
- response's design
- parameter's design
- parameter's control



### concept design

![image-20220705114206691](assets/image-20220705114206691.png)

---

![image-20220705114657898](assets/image-20220705114657898.png)

---

下面是一个具体的例子：

![image-20220705114725161](assets/image-20220705114725161.png)



### response's design

![image-20220705120310725](assets/image-20220705120310725.png)

![image-20220705120407396](assets/image-20220705120407396.png)

### parameter's design

![image-20220705120720338](assets/image-20220705120720338.png)

图中红线不能去掉，因为一个product可能会更换供应商。

### parameter's control

![image-20220705145317474](assets/image-20220705145317474.png)



## 权衡

当一个目标很难使用经典的REST思维来设计时，例如check out cart。

### 风格1

![image-20220705145638585](assets/image-20220705145638585.png)

改进思路，更接近REST架构风格。

![image-20220705145821864](assets/image-20220705145821864.png)

这里定义cart这个名词为resource，它具有status属性，check out操作对应着status的属性更新。

---

### 风格2

重新审视这个API的设计。购物车结账只是一个临时的中间状态，它是创建订单的前提。

![image-20220705150117063](assets/image-20220705150117063.png)

### 平衡

![image-20220705150510640](assets/image-20220705150510640.png)

## 回顾API设计的历史演化

- RPC - 1970s
- SOAP -  the end of 20th century
- REST
- gRPC、GraphQL



## 识别一切可能的错误反馈

- 形式错误，例如RQ中某个字段缺失，或者类型不合法。
- 业务逻辑错误。例如：转账金额>可以支持的额度。
- 服务器内部错误。例如服务器某些服务停止了，超载等导致无法正确响应。

## 返回彻底的错误反馈

![image-20220708124805635](assets/image-20220708124805635.png)

除了使用HTTP status code表示大分类，一般而言，还需要使用errorCode或者errorType(type)来明确地标识这个错误。

spurce 表示出错的字段，message只是附加的信息，来说明错误的具体描述。

---

![image-20220708125158190](assets/image-20220708125158190.png)

标识input中的某个错误的路径。

> 参阅JSON path： https://goessner.net/articles/JsonPath/

---

### 更加具体的错误说明

![image-20220708125652755](assets/image-20220708125652755.png)

```
```

### 一次一个错误 vs 一次多个错误

![image-20220708130033215](assets/image-20220708130033215.png)

第一种处理方式：对应**及早退出**的设计方法论。

第二种处理方式：对应完善的RQ参数检验，一次性返回足够完整的校验建议。

哪个更好点？有无标准答案？

没有标准答案，但是，第二种处理方式可以明显地降低网络请求往返次数。这算一个优点。



## 返回信息丰富的成功反馈

![image-20220708130948314](assets/image-20220708130948314.png)

> If the money transfer is an immediate one, we could return a 201 Created HTTP status code, which means that the transfer has been created. 
>
> For a delayed transfer, we could return a 202 Accepted response, indicating that the money transfer request has been accepted but not yet executed.

![image-20220708134431150](assets/image-20220708134431150.png)



## 组织相关的数据

![image-20220708221556519](assets/image-20220708221556519.png)

---

![image-20220708222554980](assets/image-20220708222554980.png)

## 组织目标

![image-20220708223006020](assets/image-20220708223006020.png)



## Getting credentials to consume the API

![image-20220711160721665](assets/image-20220711160721665.png)



## Defining fine-grained scopes based on concepts and actions

![image-20220712230902995](assets/image-20220712230902995.png)

## The evolution of the Banking API and its implementation

![image-20220718133423250](assets/image-20220718133423250.png)



## Grouping similar data in a list

![image-20220718160739455](assets/image-20220718160739455.png)