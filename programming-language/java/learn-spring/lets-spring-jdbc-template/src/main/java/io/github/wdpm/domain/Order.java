package io.github.wdpm.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author evan
 * @date 2020/5/20
 */
public class Order {
    private Flavor        flavor;//口味
    private int           scoops;//冰淇淋球
    private List<Topping> toppings = new ArrayList<>();//浇头
    private BigDecimal    totalPrice;

    public Order() {
    }

    public Order(Flavor flavor, int scoops, List<Topping> toppings) {
        this.flavor = flavor;
        this.scoops = scoops;
        this.toppings = toppings;
        this.totalPrice = calculatePrice();
    }

    /**
     * flavor unitPrice * scoops quantities + all topping unitPrices
     *
     * @return
     */
    private BigDecimal calculatePrice() {
        BigDecimal iceCreamCost = flavor.getUnitPrice().multiply(BigDecimal.valueOf(scoops));
        BigDecimal toppingCost  = toppings.stream().map(Topping::getUnitPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return iceCreamCost.add(toppingCost);
    }

    // getters and setters

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public int getScoops() {
        return scoops;
    }

    public void setScoops(int scoops) {
        this.scoops = scoops;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

}
