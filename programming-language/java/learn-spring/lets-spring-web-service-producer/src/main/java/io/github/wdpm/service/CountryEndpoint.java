package io.github.wdpm.service;

import io.github.wdpm.repository.CountryRepository;
import io.github.wdpm.webservice.GetCountryRequest;
import io.github.wdpm.webservice.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * @author evan
 * @date 2020/5/25
 */
// @Endpoint registers the class with Spring WS as a potential candidate for processing incoming SOAP messages.
@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://wdpm.github.io/webservice";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * The @PayloadRoot annotation is then used by Spring WS to pick the handler method,
     * based on the message’s namespace and localPart.
     *
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        return response;
    }
}