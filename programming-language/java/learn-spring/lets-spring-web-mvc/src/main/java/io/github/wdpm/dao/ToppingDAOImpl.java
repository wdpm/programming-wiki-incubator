package io.github.wdpm.dao;

import io.github.wdpm.model.Topping;
import org.springframework.stereotype.Repository;

import java.util.EnumSet;

/**
 * @author evan
 * @date 2020/5/20
 */
@Repository
public class ToppingDAOImpl implements ToppingDAO {
    @Override
    public EnumSet<?> getToppings() {
        return EnumSet.allOf(Topping.class);
    }
}
