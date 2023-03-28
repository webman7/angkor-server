package com.adplatform.restApi.domain.wallet.dao.walletchargelog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class WalletChargeLogQuerydslRepositoryImpl implements WalletChargeLogQuerydslRepository {

    private final JPAQueryFactory query;
}
