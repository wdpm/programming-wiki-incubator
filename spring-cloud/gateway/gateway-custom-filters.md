# gateway custom filters

1.override ``public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)``.
```java
@Component
public class ApiGlobalFilter implements GlobalFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            final String token = exchange.getRequest().getQueryParams().getFirst("token");
            if (StringUtils.isBlank(token)) {
                final ServerHttpResponse response = exchange.getResponse();
                final JSONObject msg = new JSONObject();
                msg.put("status",-1);
                msg.put("data","Auth error.");
                final byte[] bits = msg.toJSONString().getBytes(StandardCharsets.UTF_8);
                final DataBuffer dataBuffer = response.bufferFactory().wrap(bits);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type","text/json;charset=UTF-8");
                return response.writeWith(Mono.just(dataBuffer));
            }
            return chain.filter(exchange);
        }
    }
```

2.GET ``localhost:8080/TEST/test``, will get:
```json
{
    "data": "Auth error.",
    "status": -1
}
```