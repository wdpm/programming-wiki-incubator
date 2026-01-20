package io.github.wdpm.service;

import io.github.wdpm.domain.Ingredient;
import io.github.wdpm.domain.Order;
import io.github.wdpm.domain.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author evan
 * @date 2020/5/20
 */
@Service
public class OrderServiceImpl implements OrderService {

    private JdbcTemplate jdbcTemplate;

    private static final String CREATE_ORDER_LINE_ITEM =
            "insert into purchase_line_item(purchase_id,ingredient_id,units) values (?,?,?)";

    private static final String CREATE_ORDER = "insert into purchase(total_price) values(?)";

    // Use Intellij Alt+Enter, then `Edit Generic SQL Fragment`
    public static final String QUERY_ORDER_VIEW_BY_ORDER_ID =
            "select \n" + "p.id as pId,\n" + "p.create_timestamp as pCreateTimeStamp,\n" +
            "p.total_price as pTotalPrice,\n" + "pli.id as pliId, \n" + "pli.purchase_id as pliPurchaseId,\n" +
            "pli.ingredient_id as pliIngredientId,\n" + "pli.units as pliUnits\n" +
            "from purchase p left outer join purchase_line_item pli on p.id=pli.purchase_id where p.id=?";

    @Autowired
    public OrderServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        // create order and return orderId
        final int orderId = saveOrderInternal(order);

        // use orderId to create order items
        saveLineItem(orderId, order.getFlavor(), order.getScoops());
        order
                .getToppings()
                .forEach(topping -> saveLineItem(orderId, topping, 1));
    }

    // select 后面的参数很多的时候，手写SQL难以维护
    @Deprecated
    public List<Map<String, Object>> getOrderViewRawByOrderId(int orderId) {
        // 当数据库不支持全外连接，可以分别左外连接和右外连接，再把两个结果通过UNION合并。
        return jdbcTemplate.queryForList(QUERY_ORDER_VIEW_BY_ORDER_ID, orderId);
    }

    // recommend use customer row mapper
    public List<OrderView> getOrderViewByOrderId(int orderId) {
        return jdbcTemplate.query(QUERY_ORDER_VIEW_BY_ORDER_ID, new OrderViewMapper(), orderId);
    }

    private static class OrderViewMapper implements RowMapper<OrderView> {
        @Override
        public OrderView mapRow(ResultSet rs, int rowNum) throws SQLException {
            int pId = rs.getInt("pId");
            LocalDateTime pCreateTimeStamp = rs
                    .getTimestamp("pCreateTimeStamp")
                    .toLocalDateTime();
            BigDecimal pTotalPrice     = rs.getBigDecimal("pTotalPrice");
            int        pliId           = rs.getInt("pliId");
            int        pliPurchaseId   = rs.getInt("pliPurchaseId");
            int        pliIngredientId = rs.getInt("pliIngredientId");
            int        pliUnits        = rs.getInt("pliUnits");
            return new OrderView(pId, pCreateTimeStamp, pTotalPrice, pliId, pliPurchaseId, pliIngredientId, pliUnits);
        }
    }

    private void saveLineItem(int orderId, Ingredient ingredient, int units) {
        jdbcTemplate.update(CREATE_ORDER_LINE_ITEM, orderId, ingredient.getId(), units);
    }

    private int saveOrderInternal(Order order) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_ORDER, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, order.getTotalPrice());
            return ps;
        };
        jdbcTemplate.update(psc, keyHolder);
        return (int) keyHolder
                .getKeyList()
                .get(0)
                .get("ID");
    }
}
