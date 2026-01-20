package io.github.wdpm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author evan
 * @date 2020/5/20
 */
public class Order {
    private String        flavor;
    private int           scoops;
    private List<Topping> toppings = new ArrayList<>();

    public Order() {
    }

    public Order(String flavor, int scoops, List<Topping> toppings) {
        this.flavor = flavor;
        this.scoops = scoops;
        this.toppings = toppings;
    }

    // helpers

    public double getPrice() {
        return scoops * 1.50d + toppings.size() * 0.25d;
    }

    //getters and setters

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
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
}
