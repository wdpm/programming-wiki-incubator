package io.github.wdpm.controller;

import io.github.wdpm.model.Order;
import io.github.wdpm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author evan
 * @date 2020/5/20
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String orderForm(Model model) {
        model.addAttribute("flavors", orderService.getFlavors());
        model.addAttribute("toppings", orderService.getToppings());
        return "new-order";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createOrder(@ModelAttribute Order order, Model model) {
        double priceNumber = order.getPrice();
        String price       = NumberFormat.getCurrencyInstance(Locale.CHINA).format(priceNumber);
        model.addAttribute("price", price);
        return "order-created";
    }
}
