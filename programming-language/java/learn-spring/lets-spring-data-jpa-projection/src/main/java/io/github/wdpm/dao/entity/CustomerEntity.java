package io.github.wdpm.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author evan
 * @date 2020/5/23
 */
@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToOne(mappedBy = "customer")
    private OrderEntity order;
}
