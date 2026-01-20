package io.github.wdpm.service;

import io.github.wdpm.domain.Order;
import io.github.wdpm.domain.OrderView;

import java.util.List;
import java.util.Map;

/**
 * @author evan
 * @date 2020/5/20
 */
public interface OrderService {
    void saveOrder(Order order);

    List<Map<String, Object>> getOrderViewRawByOrderId(int orderId);

    List<OrderView> getOrderViewByOrderId(int orderId);
}

