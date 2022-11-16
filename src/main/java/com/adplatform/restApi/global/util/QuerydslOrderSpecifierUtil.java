package com.adplatform.restApi.global.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class QuerydslOrderSpecifierUtil {
    public static <T> List<OrderSpecifier<?>> getOrderSpecifier(Class<T> clazz, String variable, Sort sort) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder<T> pathBuilder = new PathBuilder<>(clazz, variable);
            orders.add(new OrderSpecifier(direction, pathBuilder.get(property)));
        });
        return orders;
    }
}
