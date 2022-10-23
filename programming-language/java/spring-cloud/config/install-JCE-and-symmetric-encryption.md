# JCE Usage
## install JCE
> https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

download it and extract，you will get these files:
```
README.txt                   This file
local_policy.jar             Unlimited strength local policy file
US_export_policy.jar         Unlimited strength US export policy file
```
first make a copy of the original JCE policy files (US_export_policy.jar and local_policy.jar). 
Then replace the strong policy files with the unlimited strength versions extracted in the previous step.
The standard place for JCE jurisdiction policy JAR files is:
```
<java-home>/lib/security           [Unix]
<java-home>\lib\security           [Windows]
```
But I found these files has already located at(in my pc):
```bash
C:\Java\jdk1.8.0_211\jre\lib\security\policy\unlimited
```

## use JCE to test symmetric encrypt
``bootstrap.yml``
```
encrypt:
  key: springcloud
```
You must disable csrf:
```java
@SpringBootConfiguration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // fix /encrypt/ forbidden
        super.configure(http);
    }
}
```
Then you can check these endpoints by http GUI tools(such as postman):
> note you should set Authorization admin/admin

- GET localhost:8102/encrypt/status
```json
{"status": "OK"}
```
- POST http://localhost:8102/encrypt
```
body: test
result: dbe13dedc998d1a5a597e5b8c116961ddce44ca329179527e9bc9d1d1a9f2f63
```
- POST http://localhost:8102/decrypt
```
body: dbe13dedc998d1a5a597e5b8c116961ddce44ca329179527e9bc9d1d1a9f2f63
result: test
```

## encrypt config content
update ``test.yml`` in git repo.
```yaml
data:
  message: '{cipher}dbe13dedc998d1a5a597e5b8c116961ddce44ca329179527e9bc9d1d1a9f2f63'
```
restart config server.Then GET ``localhost:8102/test-default.yml``
```
data:
  message: test
...
```