package com.adplatform.restApi.domain.business.dao.account;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BusinessAccountPreDeferredPaymentQuerydslRepositoryImpl implements BusinessAccountPreDeferredPaymentQuerydslRepository {
    private final JPAQueryFactory query;

}
