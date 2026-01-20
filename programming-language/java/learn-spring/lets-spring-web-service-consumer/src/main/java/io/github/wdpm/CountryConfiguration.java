package io.github.wdpm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CountryConfiguration {

    /**
     * The marshaller is pointed at the collection of generated domain objects
     * and will use them to both serialize and deserialize between XML and POJOs.
     *
     * @return
     */
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in pom.xml
        marshaller.setContextPath("io.github.wdpm.webservice.wsdl");
        return marshaller;
    }

    @Bean
    public CountryClient countryClient(Jaxb2Marshaller marshaller) {
        CountryClient client = new CountryClient();
        client.setDefaultUri("http://localhost:8080/ws");//set default uri
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}