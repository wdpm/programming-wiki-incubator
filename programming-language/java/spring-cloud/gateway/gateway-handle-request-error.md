# gateway handle request error

1.create one class ``JsonExceptionHandler`` for handling exception.
```java
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }


    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = 500;
        Throwable error = super.getError(request);
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            code = 404;
        }
        return response(code, this.buildMessage(request, error));
    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }


    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get("code");
        return HttpStatus.valueOf(statusCode);
    }


    private String buildMessage(ServerRequest request, Throwable ex) {
        //...
    }


    public static Map<String, Object> response(int status, String errorMessage) {
        //...
    }

}
```
When one http request exception occurs, firstly ``getRoutingFunction`` will be invoked.
Then it will call ``renderErrorResponse``.In the ``renderErrorResponse`` function, ``getErrorAttributes`` will be invoked.
```java
protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    boolean includeStackTrace = this.isIncludeStackTrace(request, MediaType.ALL);
    Map<String, Object> error = this.getErrorAttributes(request, includeStackTrace);
    HttpStatus errorStatus = this.getHttpStatus(error);
    return ServerResponse.status(this.getHttpStatus(error)).contentType(MediaType.APPLICATION_JSON_UTF8)
      .body(BodyInserters.fromObject(error)).doOnNext((resp) -> {
        this.logError(request, errorStatus);
    });
}
```

2.override default exception handler, and set high precedence for it.
```java
@SpringBootConfiguration
@EnableConfigurationProperties({ServerProperties.class, ResourceProperties.class})
public class ErrorHandlerConfiguration {

    //... 

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
        JsonExceptionHandler exceptionHandler = new JsonExceptionHandler(
                errorAttributes,
                this.resourceProperties,
                this.serverProperties.getError(),
                this.applicationContext);
        exceptionHandler.setViewResolvers(this.viewResolvers);
        exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
        exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
        return exceptionHandler;
    }
}
```

3.stop test module.Then GET ``localhost:8080/TEST/test?token=123``.
```json
{
    "code": 500,
    "data": null,
    "message": "Failed to handle request [GET http://localhost:8080/TEST/test?token=123]: Response status 404"
}
```