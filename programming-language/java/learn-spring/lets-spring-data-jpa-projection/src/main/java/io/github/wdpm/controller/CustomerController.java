package io.github.wdpm.controller;

import io.github.wdpm.dto.CustomerDetailsDTO;
import io.github.wdpm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/customers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDetailsDTO> getCustomers() throws Exception {
        return customerService.getCustomersAndOrderData();
    }

    @RequestMapping(value = "/customers/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDetailsDTO> searchCustomerByFirstName(
            @RequestParam("firstName") String firstName) throws Exception {
        return customerService.searchCustomerByFirstName(firstName);
    }
}
