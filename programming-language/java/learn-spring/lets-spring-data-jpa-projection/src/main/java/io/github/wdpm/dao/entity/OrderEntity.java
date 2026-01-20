package io.github.wdpm.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author evan
 * @date 2020/5/23
 */
@Data
@Entity
@Table(name = "customer_order")
public class OrderEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "total_amount")
    private String totalAmount;

    @OneToOne
    @JoinColumn(name = "customer_id",
            nullable = false)
    private CustomerEntity customer;
}
