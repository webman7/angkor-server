package com.adplatform.restApi.domain.bank.dao;

import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.bank.dto.BankDto;
import com.adplatform.restApi.domain.bank.dto.QBankDto_Response_BankInfo;
import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.media.dto.placement.PlacementDto;
import com.adplatform.restApi.domain.media.dto.placement.QPlacementDto_Response_ForSearchAll;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroupMedia.adGroupMedia;
import static com.adplatform.restApi.domain.bank.domain.QBank.bank;
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.adplatform.restApi.domain.media.domain.QPlacement.placement;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BankQuerydslRepositoryImpl implements BankQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public List<BankDto.Response.BankInfo> bankInfo() {
        return this.getListForAllQuery(null).fetch();
    }

    private JPAQuery<BankDto.Response.BankInfo> getListForAllQuery(
            Pageable pageable) {

        JPAQuery<BankDto.Response.BankInfo> query = this.query.select(new QBankDto_Response_BankInfo(
                        bank.id,
                        bank.name))
                .from(bank)
                .where(
                        bank.deleted.eq(false));

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Bank.class, "bank", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }
}
