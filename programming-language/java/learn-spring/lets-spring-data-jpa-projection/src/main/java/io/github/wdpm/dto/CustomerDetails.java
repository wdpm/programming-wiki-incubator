package io.github.wdpm.dto;

/**
 * @author evan
 * @date 2020/5/23
 */
public class CustomerDetails {

    private Integer id;

    public CustomerDetails() {
    }

    public CustomerDetails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
