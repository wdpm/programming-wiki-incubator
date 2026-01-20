package io.github.wdpm.dto;

import org.hibernate.transform.ResultTransformer;

import java.util.List;

/**
 * implements ResultTransformer
 *
 * @author evan
 * @date 2020/5/23
 */
public class CustomerDetailsTransformer implements ResultTransformer {

    public static final long serialVersionUID = 1L;

    @Override
    public Object transformTuple(Object[] rowData, String[] alias) {
        // System.out.println("alias=" + Arrays.toString(alias));//alias=[id]
        Integer customerId = (Integer) rowData[0];
        // set more...
        return new CustomerDetails(customerId);
    }

    @Override
    public List transformList(List list) {
        return list;
    }
}
