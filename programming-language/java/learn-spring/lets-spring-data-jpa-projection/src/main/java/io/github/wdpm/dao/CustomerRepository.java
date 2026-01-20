package io.github.wdpm.dao;

import io.github.wdpm.dao.entity.CustomerEntity;
import io.github.wdpm.dto.CustomerDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/23
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Query(name = "customerEntity.getCustomerDetails",
            nativeQuery = true)
    List<CustomerDetailsDTO> getCustomerDetails();

    @Query(name = "customerEntity.searchCustomerByFirstName",
            nativeQuery = true)
    List<CustomerDetailsDTO> searchCustomerByFirstName(@Param("firstName") String firstName);
}
