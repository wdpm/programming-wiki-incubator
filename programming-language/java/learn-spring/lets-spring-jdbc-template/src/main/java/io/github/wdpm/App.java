package io.github.wdpm;

import io.github.wdpm.domain.*;
import io.github.wdpm.service.IngredientService;
import io.github.wdpm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author evan
 * @date 2020/5/20
 */
@SpringBootApplication
public class App implements CommandLineRunner {
    private final Logger log = Logger.getLogger(getClass().getName());

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    IngredientService ingredientService;

    @Autowired
    OrderService orderService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        // testCustomer();

        testOrder();
    }

    private void testCustomer() {
        // test customer
        log.info("Creating tables");
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" + "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Stream
                .of("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate
                .query("SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[]{"Josh"},
                        (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"),
                                rs.getString("last_name")))
                .forEach(customer -> log.info(customer.toString()));
    }

    private void testOrder() {

        // test save order
        Flavor        flavor   = ingredientService.getFlavorById(2);
        List<Topping> toppings = ingredientService.getToppings();
        Order         order    = new Order(flavor, 2, toppings);
        orderService.saveOrder(order);

        // return List<Map<String, Object>>
        List<Map<String, Object>> list = orderService.getOrderViewRawByOrderId(1);
        list.forEach(map -> {
            map.forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });
            System.out.println("============");
        });

        // return List<OrderView>
        List<OrderView> list2 = orderService.getOrderViewByOrderId(1);
        list2.forEach(System.out::println);
    }
}
