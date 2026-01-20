package io.github.wdpm.compositekey.repository;

import io.github.wdpm.compositekey.domain.Employee;
import io.github.wdpm.compositekey.domain.EmployeeId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, EmployeeId> {

    List<Employee> findByEmployeeIdDepartmentId(Long departmentId);
}
