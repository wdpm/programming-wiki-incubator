package io.github.wdpm.service;

import io.github.wdpm.domain.Flavor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author evan
 * @date 2020/5/20
 */
@SpringBootTest
class IngredientServiceImplTest {

    @Autowired
    IngredientService ingredientService;

    @Test
    void getFlavors() {
        List<Flavor> flavors = ingredientService.getFlavors();
        System.out.println(Arrays.toString(flavors.toArray()));
    }

    @Test
    void getFlavorById() {
        Flavor flavorById = ingredientService.getFlavorById(2);
        System.out.println(flavorById);
    }
}