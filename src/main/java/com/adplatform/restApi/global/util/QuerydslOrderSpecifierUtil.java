package com.adplatform.restApi.global.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderSpecifier utility class.
 *
 * <p>{@link org.springframework.data.domain.Sort Sort}에 들어있는 {@link org.springframework.data.domain.Sort.Order Order}를 {@link com.querydsl.core.types.OrderSpecifier OrderSpecifier}로 변환한 리스트 반환한다.
 *
 * @author Seohyun Lee
 * @since 1.0
 * @see com.querydsl.core.types.OrderSpecifier OrderSpecifier
 * @see org.springframework.data.domain.Sort Sort
 */
public class QuerydslOrderSpecifierUtil {
    /**
     * Entity의 attribute로 정렬을 할 때 사용한다.
     * <p>
     * 예를 들어 Campaign 엔티티에 name이라는 attribute가 존재한다면, request params에 sort=name으로 요청 가능하다.
     * <p>
     * 한계. alias로 정렬할 수 없으며, 오로지 Entity와 연관된 attribute 이름으로만 정렬이 가능하다.
     * 만약 alias로 정렬해야 한다면, {@link QuerydslOrderSpecifierUtil#getOrderSpecifier(Sort)}를 사용한다.
     *
     * @param clazz    from 절에 사용된 Entity 클래스
     * @param variable camelcase로 작성된 Entity 클래스 이름 e.g. campaign, adGoal, adAccount
     * @param sort     sort
     * @return List
     */
    public static <T> List<OrderSpecifier> getOrderSpecifier(Class<T> clazz, String variable, Sort sort) {
        return sort.stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder<T> pathBuilder = new PathBuilder<>(clazz, variable);
            return new OrderSpecifier(direction, pathBuilder.get(property));
        }).collect(Collectors.toList());
    }

    /**
     * 별칭(alias)으로 정렬을 할 때 사용한다.
     *
     * @param sort sort
     * @return List
     */
    public static List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        return sort.stream().map(order -> new OrderSpecifier(
                        Order.valueOf(order.getDirection().name()),
                        new PathBuilder<>(Object.class, order.getProperty())))
                .collect(Collectors.toList());
    }
}
