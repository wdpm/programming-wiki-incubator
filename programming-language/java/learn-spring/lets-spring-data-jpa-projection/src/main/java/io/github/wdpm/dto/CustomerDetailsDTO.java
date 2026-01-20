package io.github.wdpm.dto;

import org.springframework.beans.factory.annotation.Value;

public interface CustomerDetailsDTO {

    Integer getCustomerId();

    @Value("#{target.firstName + ' ' + target.lastName}")
    String getCustomerName();

    String getCity();

    String getCountry();

    @Value("#{@mapperUtility.buildOrderDTO(target.orderNumber, target.totalAmount)}")
    OrderDTO getOrder();
} 
