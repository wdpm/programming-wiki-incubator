# 微服务核心概念

## 服务发现

## 服务熔断

### 熔断的核心逻辑

熔断器通过状态机模型实现，通常包含三种状态：

- Closed（闭合）：正常状态，请求直接放行，统计失败率。
- Open（断开）：当失败率超过阈值，熔断开启，直接拒绝请求（快速失败）。
- Half-Open（半开）：熔断一段时间后，尝试放行部分请求探测依赖服务是否恢复。

### 实现方式

1. 继承熔断库
主流 RPC 框架通常集成成熟的熔断库（如 Hystrix、Resilience4j、Sentinel），通过配置触发规则实现熔断。

示例 Hystrix 实现：

```java
@HystrixCommand(
  fallbackMethod = "fallbackMethod",  // 降级方法
  commandProperties = {
    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),  // 触发熔断的最小请求数
    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"), // 错误率阈值
    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") // 熔断持续时间
  }
)
public String callRemoteService() {
  // RPC 调用逻辑
}
```

2. 自带熔断逻辑
部分 RPC 框架（如 Dubbo、gRPC）内置熔断逻辑：

- Dubbo：通过 Cluster 层实现熔断（如 FailfastCluster、FailsafeCluster），结合
  org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker 的熔断策略。
- gRPC：通过客户端拦截器（Interceptor）或第三方库实现

3. 服务网格（Service Mesh）

在基础设施层（如 Istio、Linkerd）通过 Sidecar 代理实现熔断，无需修改业务代码：

```yaml
# Istio 熔断规则示例
trafficPolicy:
  outlierDetection:
    consecutiveErrors: 5  # 连续错误数
    interval: 1m         # 检测窗口
    baseEjectionTime: 30s # 最小熔断时间
```

### **熔断的关键配置参数**

| **参数**                    | **作用**                 | **典型值**                    |
| :-------------------------- | :----------------------- | :---------------------------- |
| `requestVolumeThreshold`    | 触发熔断的最小请求数     | 20（如 20 次请求中 50% 失败） |
| `errorThresholdPercentage`  | 错误率阈值（百分比）     | 50%                           |
| `sleepWindowInMilliseconds` | 熔断持续时间（毫秒）     | 5000（5 秒）                  |
| `forceOpen`                 | 强制开启熔断（手动运维） | false                         |
| `forceClosed`               | 强制关闭熔断（测试用）   | false                         |

### **熔断的降级策略（Fallback）**

熔断触发后，通常需要提供**降级逻辑**：

- **返回默认值**：如缓存数据、空结果。
- **异步补偿**：记录请求，后续重试（如通过消息队列）。
- **服务降级**：关闭非核心功能，保障主链路。

要么关闭，要么返回兜底的值，要么有补偿方案。



### 常见考虑方案

问题1：熔断阈值如何设置？

- **方案**：根据业务 SLA 调整。例如：
  - 高可用服务：错误率阈值 ≤ 10%，请求数 ≥ 50。
  - 非核心服务：错误率阈值 ≤ 30%，请求数 ≥ 20。

问题2：熔断如何监控？

- **方案**：通过 Prometheus + Grafana 监控熔断事件，或日志告警（如 ELK 捕获 `CircuitBreakerOpenException`）

## 服务跟踪

## 服务限流

## 服务监控