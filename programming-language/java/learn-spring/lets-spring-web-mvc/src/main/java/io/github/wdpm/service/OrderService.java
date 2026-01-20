package io.github.wdpm.service;

import java.util.EnumSet;

/**
 * @author evan
 * @date 2020/5/20
 */
public interface OrderService {

    // you can refactor ? to XXXVO if need
    EnumSet<?> getFlavors();

    EnumSet<?> getToppings();
}
