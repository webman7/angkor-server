package com.adplatform.restApi.global.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderSpecifier utility class.
 *
 * <p>{@link org.springframework.data.domain.Sort Sort}에 들어있는 {@link org.springframework.data.domain.Sort.Order Order}를 {@link com.querydsl.core.types.OrderSpecifier OrderSpecifier}로 변환한 리스트 반환한다.
 * @author Seohyun Lee
 * @since 1.0
 * @see com.querydsl.core.types.OrderSpecifier OrderSpecifier
 * @see org.springframework.data.domain.Sort Sort
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
