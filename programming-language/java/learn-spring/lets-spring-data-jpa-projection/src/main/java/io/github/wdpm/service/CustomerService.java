package io.github.wdpm.service;

import io.github.wdpm.dto.CustomerDetailsDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDetailsDTO> getCustomersAndOrderData() throws Exception;

    List<CustomerDetailsDTO> searchCustomerByFirstName(String firstName) throws Exception;
}
