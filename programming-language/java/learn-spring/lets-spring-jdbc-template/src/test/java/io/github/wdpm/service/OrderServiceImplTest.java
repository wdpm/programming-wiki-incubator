package io.github.wdpm.service;

import io.github.wdpm.domain.Flavor;
import io.github.wdpm.domain.Order;
import io.github.wdpm.domain.Topping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/20
 */
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    @Autowired
    IngredientService ingredientService;

    @Test
    void saveOrder() {
        Flavor        flavor   = ingredientService.getFlavorById(2);
        List<Topping> toppings = ingredientService.getToppings();
        Order         order    = new Order(flavor, 2, toppings);
        orderService.saveOrder(order);
    }
}