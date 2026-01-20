package io.github.wdpm;

import io.github.wdpm.dto.CustomerDetails;
import io.github.wdpm.dto.CustomerDetailsTransformer;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

/**
 * @author evan
 * @date 2020/5/23
 */
@SpringBootApplication
public class App {

    @PersistenceContext
    EntityManager em;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            String sql = "            SELECT\n" + "                cust.id as customerId,\n" +
                         "                cust.FIRST_NAME as firstName,\n" +
                         "                cust.LAST_NAME as lastName,\n" + "                cust.CITY as city,\n" +
                         "                cust.COUNTRY as country,\n" +
                         "                corder.ORDER_NUMBER as orderNumber,\n" +
                         "                corder.TOTAL_AMOUNT as totalAmount\n" +
                         "            FROM CUSTOMER cust LEFT JOIN CUSTOMER_ORDER corder\n" +
                         "                 ON corder.CUSTOMER_ID = cust.ID";

            testEntityManagerNativeQuery(sql);

            // testEntityManagerToMapList(sql);

            // testEntityManagerToBean();

            // testEntityManagerCustomTransform();
        };
    }

    private void testEntityManagerCustomTransform() {
        // very important: put quotation marks for the column alias like: "id"
        String sql = "select cust.id as \"id\" from Customer cust";
        List<CustomerDetails> list = em
                .createNativeQuery(sql)
                .unwrap(NativeQuery.class)
                .setResultTransformer(new CustomerDetailsTransformer())
                .list();
        list.forEach(customerDetails -> System.out.println(customerDetails.getId()));
    }

    private void testEntityManagerToBean() {
        // very important: put quotation marks for the column alias like: "id"
        String sql = "select cust.id as \"id\" from Customer cust";
        List<CustomerDetails> list = em
                .createNativeQuery(sql)
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(CustomerDetails.class))
                .list();
        list.forEach(customerDetails -> System.out.println(customerDetails.getId()));
    }

    private void testEntityManagerToMapList(String sql) {
        List list = em
                .createNativeQuery(sql)
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();
        for (Object o : list) {
            Map next = (Map) o;
            System.out.println(next.get("CUSTOMERID"));
        }
    }

    private void testEntityManagerNativeQuery(String sql) {
        Query       nativeQuery = em.createNativeQuery(sql, Tuple.class);
        List<Tuple> list        = nativeQuery.getResultList();
        list.forEach(tuple -> {
            Object customerId  = tuple.get("customerId");
            Object orderNumber = tuple.get("orderNumber");
            System.out.println(customerId + ": " + orderNumber);
        });
    }
}
