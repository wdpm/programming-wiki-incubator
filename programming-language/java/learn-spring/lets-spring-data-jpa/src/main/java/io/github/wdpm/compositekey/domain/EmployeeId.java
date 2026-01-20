package io.github.wdpm.compositekey.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.StringJoiner;

@Embeddable
public class EmployeeId implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "department_id")
    private Long departmentId;

    public EmployeeId() {
    }

    public EmployeeId(Long employeeId, Long departmentId) {
        this.employeeId = employeeId;
        this.departmentId = departmentId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeId that = (EmployeeId) o;

        if (!employeeId.equals(that.employeeId)) return false;
        return departmentId.equals(that.departmentId);
    }

    @Override
    public int hashCode() {
        int result = employeeId.hashCode();
        result = 31 * result + departmentId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EmployeeId.class.getSimpleName() + "[", "]")
                .add("employeeId=" + employeeId)
                .add("departmentId=" + departmentId)
                .toString();
    }
}
