package io.github.wdpm.service;

import io.github.wdpm.domain.Flavor;
import io.github.wdpm.domain.Topping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author evan
 * @date 2020/5/20
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public IngredientServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INGREDIENT_BY_ID = "select id, ingredient, unit_price from ingredient where id =?";

    private static final RowMapper<Flavor> flavorRowMapper = (rs, rowNum) -> {
        int        id        = rs.getInt("id");
        String     name      = rs.getString("ingredient");
        BigDecimal unitPrice = rs.getBigDecimal("unit_price");
        return new Flavor(id, name, unitPrice);
    };

    private static final RowMapper<Topping> toppingRowMapper = (rs, rowNum) -> {
        int        id        = rs.getInt("id");
        String     name      = rs.getString("ingredient");
        BigDecimal unitPrice = rs.getBigDecimal("unit_price");
        return new Topping(id, name, unitPrice);
    };

    @Override
    public List<Flavor> getFlavors() {
        String sql = "select id, ingredient, unit_price from ingredient where ingredient_type='ICE_CREAM'";
        return jdbcTemplate.query(sql, flavorRowMapper);
    }

    @Override
    public Flavor getFlavorById(int id) {
        return jdbcTemplate.queryForObject(INGREDIENT_BY_ID, flavorRowMapper, id);
    }

    @Override
    public List<Topping> getToppings() {
        String sql = "select id, ingredient, unit_price from ingredient where ingredient_type='TOPPING'";
        return jdbcTemplate.query(sql, toppingRowMapper);
    }
}
