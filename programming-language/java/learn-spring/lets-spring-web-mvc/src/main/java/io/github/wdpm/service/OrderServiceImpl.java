package io.github.wdpm.service;

import io.github.wdpm.dao.FlavorDAO;
import io.github.wdpm.dao.ToppingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

/**
 * @author evan
 * @date 2020/5/20
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    FlavorDAO flavorDAO;

    @Autowired
    ToppingDAO toppingDAO;

    @Override
    public EnumSet<?> getFlavors() {
        return flavorDAO.getFlavors();
    }

    @Override
    public EnumSet<?> getToppings() {
        return toppingDAO.getToppings();
    }
}
