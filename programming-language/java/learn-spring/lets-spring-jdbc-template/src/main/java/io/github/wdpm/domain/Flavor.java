package io.github.wdpm.domain;

import java.math.BigDecimal;

/**
 * 口味
 *
 * @author evan
 * @date 2020/5/20
 */
public class Flavor extends Ingredient {
    public Flavor(Integer id, String name, BigDecimal unitPrice) {
        super(id, name, unitPrice);
    }
}
