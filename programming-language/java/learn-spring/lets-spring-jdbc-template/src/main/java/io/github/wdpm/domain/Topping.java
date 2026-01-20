package io.github.wdpm.domain;

import java.math.BigDecimal;

/**
 * 浇头
 *
 * @author evan
 * @date 2020/5/20
 */
public class Topping extends Ingredient {
    public Topping(Integer id, String name, BigDecimal unitPrice) {
        super(id, name, unitPrice);
    }
}
