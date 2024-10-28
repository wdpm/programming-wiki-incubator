# 浏览器跨tab通信



|           | Cookie                  | IndexedDB               | LocalStorage Event      | Broadcast Channel          | ServiceWorker                          | window.postMessage      |
| --------- | ----------------------- | ----------------------- | ----------------------- | -------------------------- | -------------------------------------- | ----------------------- |
| 实现方式  | setInterval()轮询数据源 | setInterval()轮询数据源 | event listener callback | message channel            | event listener callback                | event listener callback |
| 模式      | consumer pull           | consumer pull           | producer push           | producer push              | producer push                          | producer push           |
| 同步/异步 | 同步                    | 异步                    | 同步                    | 异步                       | 异步                                   | 异步                    |
| 支持跨域  | 否                      | 否                      | 否                      | 否                         | 否                                     | 是🏆                     |
| 通信效率  | 低                      | 高🏆                     | 低                      | 高🏆                        | 高🏆                                    | 高🏆                     |
| 缺点      | 支持数据量小            |                         | value 只支持 string     | 浏览器兼容性较差           | 本职作用不是用于消息通信，引入成本较大 |                         |
| 优点      |                         |                         |                         | 消息对象可以是 Object 类型 |                                        | 支持跨域                |



## 参考

- [浏览器跨tab通信的几种办法](https://www.hateonion.me/posts/21oct14#windowpostmessage)

