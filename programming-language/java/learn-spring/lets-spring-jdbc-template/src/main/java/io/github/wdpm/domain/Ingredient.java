package io.github.wdpm.domain;

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * @author evan
 * @date 2020/5/20
 */
public abstract class Ingredient {
    private Integer    id;
    private String     name;
    private BigDecimal unitPrice;

    public Ingredient(Integer id, String name, BigDecimal unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ingredient.class.getSimpleName() + "[", "]").add("id=" + id)
                                                                                  .add("name='" + name + "'")
                                                                                  .add("unitPrice=" + unitPrice)
                                                                                  .toString();
    }
}
