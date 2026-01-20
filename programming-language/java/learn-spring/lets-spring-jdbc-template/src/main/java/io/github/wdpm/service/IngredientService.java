package io.github.wdpm.service;

import io.github.wdpm.domain.Flavor;
import io.github.wdpm.domain.Topping;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/20
 */
public interface IngredientService {
    List<Flavor> getFlavors();

    List<Topping> getToppings();

    Flavor getFlavorById(int id);
}
