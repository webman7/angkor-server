package com.adplatform.restApi.domain.wallet.dao.walletmaster;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;

@Slf4j
@RequiredArgsConstructor
@Repository
public class WalletMasterQuerydslRepositoryImpl implements WalletMasterQuerydslRepository {

    private final JPAQueryFactory query;

}
