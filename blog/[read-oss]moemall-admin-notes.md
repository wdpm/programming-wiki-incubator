# moemall-admin 源码阅读笔记

[管理后台](https://github.com/chanshiyucx/moemall-admin), 该项目已被删除。

## 概述

技术栈

| 技术                   | 版本     | 说明           |
|----------------------|--------|--------------|
| Spring Boot          | 2.1.8  | 容器 +MVC 框架   |
| Spring Cloud Alibaba | 2.1.0  | 阿里巴巴微服务      |
| Spring Security      | 5.1.6  | 认证和授权框架      |
| MySql                | 8.0.17 | 关系型数据库       |
| MyBatis              | 4.1.5  | ORM 框架       |
| MyBatisGenerator     | 2.1.5  | 数据层代码生成      |
| PageHelper           | 1.2.12 | MyBatis 分页插件 |
| JWT                  | 0.9.0  | JWT 登录支持     |
| Lombok               | 1.18.8 | 简化对象封装工具     |
| Swagger-UI           | 2.9.2  | 文档生产工具       |
| Redis                | 2.1.10 | 分布式缓存        |
| Elasticsearch        | -      | 搜索引擎         |
| RabbitMq             | -      | 消息队列         |
| Docker               | -      | 应用容器引擎       |

通用服务，一共四个模块。

| 项目              | 描述      |
|-----------------|---------|
| moemall-common  | 通用的工具类库 |
| moemall-mbg     | 通用的代码生成 |
| moemall-service | 通用的服务层  |
| moemall-admin   | 后台管理模块  |

## moemall-common

类似于 utils 层，通用的工具函数就丢这里吧。

## moemall-service

```
// src/main/java/com/chanshiyu/moemall/service
├───exception
│       AuthenticationException.java
│       BadRequestException.java
│       BaseException.java  
├───handler
│       RestExceptionHandler.java
├───utils
│       RedisUtil.java
│       SpringUtil.java
└───vo
        CommonListResult.java
        CommonResult.java
        ResultAttributes.java
```

这里其实也是一些通用的代码，在这个 demo 项目中，定义成 service 模块其实不妥。或许可以合并进入 common 模块。

## moemall-mbg

```
java
  com/chanshiyu/moemall/mbg
    mapper
      OmsOrderMapper.java
      ...
    model
      OmsOrder.java
      ...
  tk/mybatis/mapper
    MyMapper.java
```

`OmsOrderMapper.java`

```java
package com.chanshiyu.moemall.mbg.mapper;

import com.chanshiyu.moemall.mbg.model.OmsOrder;
import tk.mybatis.mapper.MyMapper;

public interface OmsOrderMapper extends MyMapper<OmsOrder> {
}
```

mapper 的实现仅仅是继承了 tk.mybatis 包的接口。

---

`OmsOrder.java`

```java
package com.chanshiyu.moemall.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@ApiModel("订单表")
@Table(name = "oms_order")
public class OmsOrder implements Serializable {
    /**
     * 订单 id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("订单id")
    private Long id;

    @Column(name = "member_id")
    @ApiModelProperty("")
    private Long memberId;

    @Column(name = "coupon_id")
    @ApiModelProperty("")
    private Long couponId;

    // ... 省略字段

    private static final long serialVersionUID = 1L;
}
```

对于数据模型层的定义，使用 lombok 减少样板代码，使用 `javax.persistence.*` 标注来标记表格 @Table，使用
swagger 标注来标注 API 实现。
---

`tk/mybatis/mapper/MyMapper.java` 的实现，依旧是扩展接口。

```java
package tk.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
```

下面来看下 resources 目录组织。

```
resources
  generator
    generatorConfig.xml
    jdbc.properties
  mapper
    OmsOrderMapper.xml
    ...
```

`generatorConfig.xml` 关键代码片段：

```xml
?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE generatorConfiguration
                PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
                "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!-- 引入数据库连接配置 -->
    <properties resource="generator/jdbc.properties"/>

    <context id="Mysql" targetRuntime="MyBatis3Simple" defaultModelType="flat">
        <!-- 这里省略数据链接属性等 -->

        <!-- 配置实体类存放路径 -->
        <javaModelGenerator targetPackage="com.chanshiyu.moemall.mbg.model" targetProject="src/main/java"/>

        <!-- 配置 XML 存放路径 -->
        <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources"/>

        <!-- 配置 DAO 存放路径 -->
        <javaClientGenerator targetPackage="com.chanshiyu.moemall.mbg.mapper"
                             targetProject="src/main/java"
                             type="XMLMAPPER"/>

        <!-- 配置需要指定生成的数据表，% 代表所有表 -->
        <table tableName="${tableName}">
            <!-- mysql 配置 -->
            <generatedKey column="id" sqlStatement="Mysql" identity="true"/>
        </table>
    </context>
</generatorConfiguration>
```

这里是从已有的数据库模式中，推导衍生出 model 层、mapper 接口层、mapper 字段定义的 xml 层。

model 层（Java 代码）和 mapper 接口层（Java 代码）在上面已经描述，下面只关注 mapper 字段定义的 xml 层。

取 `OmsOrderMapper.xml` 作为例子进行分析：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.chanshiyu.moemall.mbg.mapper.OmsOrderMapper">
    <resultMap id="BaseResultMap" type="com.chanshiyu.moemall.mbg.model.OmsOrder">
        <!--
          WARNING - @mbg.generated
        -->
        <id column="id" jdbcType="BIGINT" property="id"/>
        <result column="member_id" jdbcType="BIGINT" property="memberId"/>
        <result column="coupon_id" jdbcType="BIGINT" property="couponId"/>
        <result column="order_sn" jdbcType="VARCHAR" property="orderSn"/>
        <result column="create_time" jdbcType="TIMESTAMP" property="createTime"/>
        <result column="member_username" jdbcType="VARCHAR" property="memberUsername"/>
        <result column="total_amount" jdbcType="DECIMAL" property="totalAmount"/>
        <result column="pay_amount" jdbcType="DECIMAL" property="payAmount"/>
        <result column="freight_amount" jdbcType="DECIMAL" property="freightAmount"/>
        <result column="promotion_amount" jdbcType="DECIMAL" property="promotionAmount"/>
        <result column="integration_amount" jdbcType="DECIMAL" property="integrationAmount"/>
        <result column="coupon_amount" jdbcType="DECIMAL" property="couponAmount"/>
        <result column="discount_amount" jdbcType="DECIMAL" property="discountAmount"/>
        <result column="pay_type" jdbcType="INTEGER" property="payType"/>
        <result column="source_type" jdbcType="INTEGER" property="sourceType"/>
        <result column="status" jdbcType="INTEGER" property="status"/>
        <result column="order_type" jdbcType="INTEGER" property="orderType"/>
        <result column="delivery_company" jdbcType="VARCHAR" property="deliveryCompany"/>
        <result column="delivery_sn" jdbcType="VARCHAR" property="deliverySn"/>
        <result column="auto_confirm_day" jdbcType="INTEGER" property="autoConfirmDay"/>
        <result column="integration" jdbcType="INTEGER" property="integration"/>
        <result column="growth" jdbcType="INTEGER" property="growth"/>
        <result column="promotion_info" jdbcType="VARCHAR" property="promotionInfo"/>
        <result column="bill_type" jdbcType="INTEGER" property="billType"/>
        <result column="bill_header" jdbcType="VARCHAR" property="billHeader"/>
        <result column="bill_content" jdbcType="VARCHAR" property="billContent"/>
        <result column="bill_receiver_phone" jdbcType="VARCHAR" property="billReceiverPhone"/>
        <result column="bill_receiver_email" jdbcType="VARCHAR" property="billReceiverEmail"/>
        <result column="receiver_name" jdbcType="VARCHAR" property="receiverName"/>
        <result column="receiver_phone" jdbcType="VARCHAR" property="receiverPhone"/>
        <result column="receiver_post_code" jdbcType="VARCHAR" property="receiverPostCode"/>
        <result column="receiver_province" jdbcType="VARCHAR" property="receiverProvince"/>
        <result column="receiver_city" jdbcType="VARCHAR" property="receiverCity"/>
        <result column="receiver_region" jdbcType="VARCHAR" property="receiverRegion"/>
        <result column="receiver_detail_address" jdbcType="VARCHAR" property="receiverDetailAddress"/>
        <result column="note" jdbcType="VARCHAR" property="note"/>
        <result column="confirm_status" jdbcType="INTEGER" property="confirmStatus"/>
        <result column="delete_status" jdbcType="INTEGER" property="deleteStatus"/>
        <result column="use_integration" jdbcType="INTEGER" property="useIntegration"/>
        <result column="payment_time" jdbcType="TIMESTAMP" property="paymentTime"/>
        <result column="delivery_time" jdbcType="TIMESTAMP" property="deliveryTime"/>
        <result column="receive_time" jdbcType="TIMESTAMP" property="receiveTime"/>
        <result column="comment_time" jdbcType="TIMESTAMP" property="commentTime"/>
        <result column="modify_time" jdbcType="TIMESTAMP" property="modifyTime"/>
    </resultMap>
</mapper>
```

`<result` 标签中 column 的值是数据表中列名，这里是小写下划线的约定；property 的值是 Java 模型层的字段名，采用小驼峰的约定。

## moemall-admin

`src/main/java/com/chanshiyu/moemall/admin`

```bash
aspect/
config/
controller/
dao/
model/
security/
service/
validator/
AdminApplication.java
```

### aspect AOP

`WebLogAspect.java`

```java

@Slf4j
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    @Pointcut("execution(public * com.chanshiyu.moemall.admin.controller.*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // omitted
    }

    private Object getParameter(Method method, Object[] args) {
        // omitted
    }
}
```

- 首先顶一个切点：webLog()，这个切点是对于这个包 com.chanshiyu.moemall.admin.controller 下所有文件的所有方法。
- doBefore 空实现
- doAfterReturning(Object ret) 基本等于空实现，只是标记了结果为 ret
- doAround(ProceedingJoinPoint joinPoint) 是关键实现

我们下面来看一下这个方法究竟做了什么：

```java

@Around("webLog()")
public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    // 获取当前请求对象
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    assert attributes != null;
    HttpServletRequest request = attributes.getRequest();
    // 记录请求信息
    WebLog webLog = new WebLog();
    Object result = joinPoint.proceed();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    if (method.isAnnotationPresent(ApiOperation.class)) {
        ApiOperation log = method.getAnnotation(ApiOperation.class);
        webLog.setDescription(log.value());
    }
    long endTime = System.currentTimeMillis();
    String urlStr = request.getRequestURL().toString();
    webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
    webLog.setIp(request.getRemoteAddr());
    webLog.setMethod(request.getMethod());
    webLog.setParameter(getParameter(method, joinPoint.getArgs()));
    webLog.setResult(result);
    webLog.setStartTime(DateUtil.date(startTime).toString());
    webLog.setSpendTime((int) (endTime - startTime));
    webLog.setUri(request.getRequestURI());
    webLog.setUrl(request.getRequestURL().toString());
    log.info("请求日志: {}", JSONUtil.parse(webLog).toString());
    return result;
}
```

核心就是从 RequestContextHolder 中取出 request，然后从 request 对象中抽取需要的信息，将这些信息整合到创建的 webLog 对象中。
最后，打印日志。

### config

对 CORS、RedisTemplate、SwaggerConfig 进行配置。

### controller

API 的门户，前端请求进入应用层后，一般是先到这里。XXController 往往会自动注入所需的 XXService 实例。

### DAO 层

以 `OmsOrderDao` 为例，这里仅仅是定义方法签名。

```java
public interface OmsOrderDao {

    /**
     * 条件查询订单
     */
    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单详情
     */
    OmsOrderDetail getDetail(@Param("id") Long id);

}
```

它对应的实际实现位于 `src/main/resources/dao/OmsOrderDao.xml`

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.chanshiyu.moemall.admin.dao.OmsOrderDao">
    <resultMap id="orderDetailResultMap" type="com.chanshiyu.moemall.admin.model.dto.OmsOrderDetail"
               extends="com.chanshiyu.moemall.mbg.mapper.OmsOrderMapper.BaseResultMap">
        <collection property="orderItemList"
                    resultMap="com.chanshiyu.moemall.mbg.mapper.OmsOrderItemMapper.BaseResultMap" columnPrefix="item_"/>
        <collection property="historyList"
                    resultMap="com.chanshiyu.moemall.mbg.mapper.OmsOrderOperateHistoryMapper.BaseResultMap"
                    columnPrefix="history_"/>
    </resultMap>

    <select id="getList" resultMap="com.chanshiyu.moemall.mbg.mapper.OmsOrderMapper.BaseResultMap">
        <!--   omitted   -->
    </select>
    <update id="delivery">
        <!--   omitted   -->
    </update>
  <select id="getDetail" resultMap="orderDetailResultMap">
    SELECT o.*,
        oi.id item_id,
        oi.product_id item_product_id,
        oi.product_sn item_product_sn,
        oi.product_pic item_product_pic,
        oi.product_name item_product_name,
        oi.product_brand item_product_brand,
        oi.product_price item_product_price,
        oi.product_quantity item_product_quantity,
        oi.product_attr item_product_attr,
        oi.sp1 item_sp1,
        oi.sp2 item_sp2,
        oi.sp3 item_sp3,
        oh.id history_id,
        oh.operate_man history_operate_man,
        oh.create_time history_create_time,
        oh.order_status history_order_status,
        oh.note history_note
    FROM
        oms_order o
        LEFT JOIN oms_order_item oi ON o.id = oi.order_id
        LEFT JOIN oms_order_operate_history oh ON o.id = oh.order_id
    WHERE
        o.id = #{id}
        ORDER BY oi.id ASC,oh.create_time DESC
  </select>
</mapper>
```

- 这里我们只关注 getDetail 的 select 查询，忽略其他 CRUD 的 SQL 拼接实现，将关注点放于返回值的定义。
  我们可以看到返回的列定义中，存在 item_product_id 或者 history_operate_man 这类含有 `item_` 以及 `history_` 前缀的命名变量。
- orderDetailResultMap 这个返回值的类型为 OmsOrderDetail。位置：
  `src/main/java/com/chanshiyu/moemall/admin/model/dto/OmsOrderDetail.java`

```java

@EqualsAndHashCode(callSuper = true)
@Data
public class OmsOrderDetail extends OmsOrder {

    private List<OmsOrderItem> orderItemList;

    private List<OmsOrderOperateHistory> historyList;

}
```

可以看到 xml 定义中 `<collection property="orderItemList"` 和 OmsOrderDetail 数据类中的字段 orderItemList
是一一对应的。historyList 同理。

XMl 中 collection 的配置中，处理了 columnPrefix：

```xml
<collection property="orderItemList"
            resultMap="com.chanshiyu.moemall.mbg.mapper.OmsOrderItemMapper.BaseResultMap"
            columnPrefix="item_"/>
<collection property="historyList"
            resultMap="com.chanshiyu.moemall.mbg.mapper.OmsOrderOperateHistoryMapper.BaseResultMap"
            columnPrefix="history_"/>
```

这样，例如 item_product_id 就可以被识别为 oms_order_item 表中的 product_id，映射关系得到了正确处理。

### model 层

```
model/
  bo
  dto
  params
  vo
```

- bo(Business Object)：用于业务逻辑和内部处理。
- dto：用于层与层之间的数据传递。
- params：用于接收请求的参数。一般用于 RQ，对应请求参数。定义成 requestParams 包或许更加准确。
- vo(View Object)：用于将数据返回给客户端或前端视图。数据库的结构定义不完全适用于前端展示，因此需要再次修饰，于是变成了 VO。

可以看到数据模型层，因为位于不同的层级，或者为了方便处理，分割成了几个层次。

- 关于 DTO 这一层，这里存放的是跨层传输的数据对象定义，如果系统内部的确含有很多边界，这一层抽象是合理且必要的。
  如果你在软件编写时发现自己需要从一个数据对象转化为另一个相似的数据对象，你往往需要的就是这一层。本质原因还是数据库字段设计不完全适用于系统内部或者前端展示。
- bo 这一层，业务对象。这里有一个 WebLog.java 作为案例。它指的是用户操作的记录，记录了 username、url、ip、user-agent、method
  等等信息。是一个高度聚焦业务逻辑的层次。

### service 层

经典的先定义 interface，然后 impl 的模块。在这个模块的实现中，往往会引用 DAO 实例或者 mapper 实例，来辅助数据库的 CRUD。

下面是一个例子。

`OmsCompanyAddressService.java`

```java
public interface OmsCompanyAddressService {
    /**
     * 获取全部收货地址
     */
    List<OmsCompanyAddress> list();
}
```

`OmsCompanyAddressServiceImpl.java`

```java
@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {
    @Autowired
    private OmsCompanyAddressMapper companyAddressMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OmsCompanyAddress> list() {
        return companyAddressMapper.selectAll();
    }
}
```

下面我们仔细看下数据库 ACID 中的 A 如何体现。也就是常规数据库相关操作中，如何保证事务。
你可能已经注意到了上面的这一行标注 `@Transactional(propagation = Propagation.SUPPORTS)`，
那么你可能会好奇有多少种枚举，每一种枚举代表了什么含义，分别对应什么使用场景？

```java
package org.springframework.transaction.annotation;

public enum Propagation {
    REQUIRED(0),
    SUPPORTS(1),
    MANDATORY(2),
    REQUIRES_NEW(3),
    NOT_SUPPORTED(4),
    NEVER(5),
    NESTED(6);

    private final int value;

    private Propagation(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
```

枚举解释：

| 传播行为      | 值  |  当前事务存在时  |  当前事务不存在时  | 说明                                |
|-----------------|---------|---------------|----------------|-----------------------------------|
| `REQUIRED`      | 0       | 加入当前事务        | 创建新事务          | 最常用的选择。适用于写操作。                    |
| `SUPPORTS`      | 1       | 加入当前事务        | 非事务运行          | 操作对事务不敏感，但是支持事务，例如只读查询。           |
| `MANDATORY`     | 2       | 加入当前事务        | 抛出异常           | 需要确保操作已经在事务上下文中运行。                |
| `REQUIRES_NEW`  | 3       | 挂起当前事务，创建新事务  | 创建新事务          | 需要强隔离的操作，例如记录日志、发送通知等。            |
| `NOT_SUPPORTED` | 4       | 挂起当前事务，非事务运行  | 非事务运行          | 操作不需要事务支持，例如性能敏感的只读操作。            |
| `NEVER`         | 5       | 抛出异常          | 非事务运行          | 明确不能在事务环境下运行的操作。                  |
| `NESTED`        | 6       | 创建嵌套事务        | 创建新事务          | 嵌套事务与外部事务是父子关系，子事务为单独回滚，但依赖父事务提交。 |

有关这些传播行为的区别，可以自行做试验来验证。

### security

这里只关注 JWT 的实现: JwtTokenUtil.java。

```
 * JWT token 的格式：header.payload.signature
 
 * header 的格式（算法、token 的类型）：
 * {"alg": "HS512","typ": "JWT"}
 
 * payload 的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 
 * signature 的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
```

```java
/**
 * 获取请求头部的 token
 */
public String getToken(HttpServletRequest request) {
    String authHeader = request.getHeader(tokenHeader);
    if (authHeader != null && authHeader.startsWith(tokenHead)) {
        return authHeader.substring(tokenHead.length());
    }
    return null;
}

/**
 * 生成 JWT 的 token
 */
private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
            .setClaims(claims)
            .setExpiration(generateExpirationDate())
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
}
```

claims 中可以取 expiration、audience、subject 等属性。

| 字段名            | 缩写    | 描述   | 典型用途                  |
|----------------|-------|------|-----------------------|
| ISSUER         | `iss` | 签发者  | 验证 JWT 的来源            |
| **SUBJECT**    | `sub` | 主题   | 指定 JWT 关联的用户或实体       |
| AUDIENCE       | `aud` | 受众   | 确保 JWT 只被特定服务接收       |
| **EXPIRATION** | `exp` | 过期时间 | 限制 JWT 的有效期           |
| NOT_BEFORE     | `nbf` | 生效时间 | JWT 必须在某个时间之后才有效      |
| **ISSUED_AT**  | `iat` | 签发时间 | 指示 JWT 的生成时间，防止时间相关攻击 |
| ID             | `jti` | 唯一标识 | 防止令牌重复使用（重放攻击）        |

最后，说明一下 token 刷新的处理。

```java
/**
 * 判断 token 是否可以被刷新
 */
public boolean canRefresh(String token) {
    return !isTokenExpired(token);
}

/**
 * 刷新 token
 */
public String refreshToken(String token) {
    Claims claims = getClaimsFromToken(token);
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
}
```

token 可以被刷新的前提是还没过期。刷新 token 本质上是根据当前时间来重新创建一个新 token 返回。

## 数据表模块与前缀名区分

```
cms_subject cms_前缀表示内容管理系统
oms_order   oms_前缀表示订单管理系统
pms_album   pms_前缀表示商品管理系统
sms_coupon  sms_前缀可是能服务管理系统？
ums_admin   ums_前缀是用户管理系统
```
将前缀约定写入数据库表名，这样很容易理解区分。
```java
@Data
@ApiModel("后台用户表")
@Table(name = "ums_admin")
public class UmsAdmin implements Serializable {}
```


## ORM框架部分更新的机制设计

`returnReasonMapper.updateByExampleSelective(record, example);`

参数含义：

- record：
    - 包含需要更新的数据，通常是一个实体类的实例。
    - 只有非空字段才会被更新（体现了 Selective 的特点）。
- example：
    - 包含更新条件的对象，用于生成 SQL 中的 WHERE 子句。