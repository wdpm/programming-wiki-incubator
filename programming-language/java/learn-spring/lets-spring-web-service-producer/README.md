# Produce Web Service
## Steps
1. define country.xsd file
2. 利用Maven插件jaxb2-maven-plugin从XSD文件生成Java领域类。生成目录位于`target/generated-sources/jaxb/`
3. 定义CountryRepository，使用第2步生成的Java领域类创建数据服务。（类似于service层）
4. 创建CountryEndpoint，并利用countryRepository定义外部API接口。（类似于controller层）
5. 创建WebServiceConfig，暴露*.wsdl定义路径，并对外服务。

## Test
under folder `src\test\resources`, create `request.xml` as below: 
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:ws="http://wdpm.github.io/webservice">
    <soapenv:Header/>
    <soapenv:Body>
        <ws:getCountryRequest>
            <ws:name>Spain</ws:name>
        </ws:getCountryRequest>
    </soapenv:Body>
</soapenv:Envelope>

```
```bash
curl --header "content-type: text/xml" -d @request.xml http://localhost:8080/ws > response.xml
```
`response.xml`
```xml
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    <SOAP-ENV:Header/>
    <SOAP-ENV:Body>
        <ns2:getCountryResponse xmlns:ns2="http://wdpm.github.io/webservice">
            <ns2:country>
                <ns2:name>Spain</ns2:name>
                <ns2:population>46704314</ns2:population>
                <ns2:capital>Madrid</ns2:capital>
                <ns2:currency>EUR</ns2:currency>
            </ns2:country>
        </ns2:getCountryResponse>
    </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

## Reference Docs
- [producing-web-service](https://spring.io/guides/gs/producing-web-service/)
