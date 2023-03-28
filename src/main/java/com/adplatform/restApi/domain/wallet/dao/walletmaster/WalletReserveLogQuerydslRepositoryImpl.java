package com.adplatform.restApi.domain.wallet.dao.walletmaster;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class WalletReserveLogQuerydslRepositoryImpl implements WalletReserveLogQuerydslRepository {
    private final JPAQueryFactory query;
}
