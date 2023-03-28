package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.*;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.dashboard.dto.DashboardDto;
import com.adplatform.restApi.domain.advertiser.dashboard.dto.QDashboardDto_Response_AdAccountCountByAd;
import com.adplatform.restApi.domain.company.dto.QCompanyDto_Response_AdAccountDetail;
import com.adplatform.restApi.domain.statistics.domain.sale.QSaleAdAccountDaily;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletBalance;
import com.adplatform.restApi.domain.wallet.dto.QWalletDto_Response_WalletSpend;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto.Request;
import static com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto.Response;
import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.advertiser.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreative.creative;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.statistics.domain.report.QReportAdGroupDaily.reportAdGroupDaily;
import static com.adplatform.restApi.domain.statistics.domain.sale.QSaleAdAccountDaily.saleAdAccountDaily;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.wallet.domain.QWalletMaster.walletMaster;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;

/**
 * @author junny
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class AdAccountQuerydslRepositoryImpl implements AdAccountQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Response.ForAdminSearch> searchForAdmin(Pageable pageable, Request.ForAdminSearch request) {
        List<Response.ForAdminSearch> content = this.getSearchForAdminQuery(pageable, request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                ).groupBy(adAccount.id);
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public List<Response.ForAdminSearch> searchForAdmin(Request.ForAdminSearch request) {
        return this.getSearchForAdminQuery(null, request).fetch();
    }

    private JPAQuery<Response.ForAdminSearch> getSearchForAdminQuery(
            Pageable pageable,
            Request.ForAdminSearch request) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        JPAQuery<Response.ForAdminSearch> query = this.query.select(
                        new QAdAccountDto_Response_ForAdminSearch(
                                adAccount.id,
                                adAccount.businessAccount,
                                adAccount.name,
                                as(select(user.name)
                                                .from(user)
                                                .join(adAccountUser).on(user.id.eq(adAccountUser.id.userId),
                                                        adAccountUser.memberType.eq(AdAccountUser.MemberType.MASTER))
                                                .where(adAccountUser.id.adAccountId.eq(adAccount.id)),
                                        "marketerName"),
                                new QWalletDto_Response_WalletSpend(
                                        as(select(walletMaster.availableAmount.sum())
                                                        .from(walletMaster)
                                                        .where(walletMaster.id.eq(adAccount.businessAccount.id)),
                                                "cash"),
                                        as(select(reportAdGroupDaily.information.cost.sum())
                                                        .from(reportAdGroupDaily)
                                                        .where(reportAdGroupDaily.adAccountId.eq(adAccount.id),
                                                                reportAdGroupDaily.reportDate.eq(Integer.valueOf(now.format(yyyyMMdd)))),
                                                "todaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(saleAdAccountDaily.id.adAccountId.eq(adAccount.id),
                                                                saleAdAccountDaily.id.statDate.eq(Integer.valueOf(now.minusDays(1L).format(yyyyMMdd)))),
                                                "yesterdaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(
                                                                saleAdAccountDaily.id.adAccountId.eq(adAccount.id),
                                                                saleAdAccountDaily.id.statDate.between(
                                                                        Integer.valueOf(now.withDayOfMonth(1).format(yyyyMMdd)),
                                                                        Integer.valueOf(now.withDayOfMonth(now.lengthOfMonth()).format(yyyyMMdd))
                                                                )
                                                        ),
                                                "monthSpendQuery")
                                ),
                                adAccount.config,
                                adAccount.adminStop,
                                adAccount.outOfBalance
                        )
                )
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .groupBy(
                        adAccount.id
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Page<Response.ForAgencySearch> searchForAgency(Pageable pageable, Request.ForAgencySearch request, Integer userId) {
        List<Response.ForAgencySearch> content = this.getSearchForAgencyQuery(pageable, request, userId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        adAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                ).groupBy(adAccount.id);
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public List<Response.ForAgencySearch> searchForAgency(Request.ForAgencySearch request, Integer userId) {
        return this.getSearchForAgencyQuery(null, request, userId).fetch();
    }

    private JPAQuery<Response.ForAgencySearch> getSearchForAgencyQuery(
            Pageable pageable,
            Request.ForAgencySearch request,
            Integer userId) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        JPAQuery<Response.ForAgencySearch> query = this.query.select(
                        new QAdAccountDto_Response_ForAgencySearch(
                                adAccount.id,
                                adAccount.businessAccount,
                                adAccount.name,
                                as(select(user.name)
                                                .from(user)
                                                .join(adAccountUser).on(user.id.eq(adAccountUser.id.userId),
                                                        adAccountUser.memberType.eq(AdAccountUser.MemberType.MASTER))
                                                .where(adAccountUser.id.adAccountId.eq(adAccount.id)),
                                        "marketerName"),
                                new QWalletDto_Response_WalletSpend(
                                        as(select(walletMaster.availableAmount.sum())
                                                        .from(walletMaster)
                                                        .where(walletMaster.id.eq(adAccount.businessAccount.id)),
                                                "cash"),
                                        as(select(reportAdGroupDaily.information.cost.sum())
                                                        .from(reportAdGroupDaily)
                                                        .where(reportAdGroupDaily.adAccountId.eq(adAccount.id),
                                                                reportAdGroupDaily.reportDate.eq(Integer.valueOf(now.format(yyyyMMdd)))),
                                                "todaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(saleAdAccountDaily.id.adAccountId.eq(adAccount.id),
                                                                saleAdAccountDaily.id.statDate.eq(Integer.valueOf(now.minusDays(1L).format(yyyyMMdd)))),
                                                "yesterdaySpend"),
                                        as(select(saleAdAccountDaily.supplyAmount.sum())
                                                        .from(saleAdAccountDaily)
                                                        .where(
                                                                saleAdAccountDaily.id.adAccountId.eq(adAccount.id),
                                                                saleAdAccountDaily.id.statDate.between(
                                                                        Integer.valueOf(now.withDayOfMonth(1).format(yyyyMMdd)),
                                                                        Integer.valueOf(now.withDayOfMonth(now.lengthOfMonth()).format(yyyyMMdd))
                                                                )
                                                        ),
                                                "monthSpendQuery")
                                ),
                                adAccount.config,
                                adAccount.adminStop,
                                adAccount.outOfBalance
                        )
                )
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        adAccountUser.id.userId.eq(userId),
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .groupBy(
                        adAccount.id
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Page<Response.ForAdvertiserSearch> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserNo, AdAccountUser.Status status) {
        List<Response.ForAdvertiserSearch> content = this.getSearchForAdvertiserQuery(pageable, id, name, loginUserNo, status)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(Wildcard.count)
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccountUser.user, user)
                .where(
                        adAccountUser.id.userId.eq(loginUserNo),
                        adAccountUser.status.eq(status),
                        this.eqId(id),
                        this.containsName(name));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Response.ForAdvertiserSearch> searchForAdvertiser(
            Integer id, String name, Integer loginUserNo, AdAccountUser.Status status) {
        return this.getSearchForAdvertiserQuery(null, id, name, loginUserNo, status).fetch();
    }

    private JPAQuery<Response.ForAdvertiserSearch> getSearchForAdvertiserQuery(
            Pageable pageable,
            Integer id, String name, Integer loginUserNo, AdAccountUser.Status status) {

        JPAQuery<Response.ForAdvertiserSearch> query = this.query.select(new QAdAccountDto_Response_ForAdvertiserSearch(
                        adAccount.id,
                        adAccount.name,
//                        as(select(user.loginId)
//                                        .from(user)
//                                        .where(user.id.in(select(adAccountUser.id.userId)
//                                                .from(adAccountUser)
//                                                .where(adAccountUser.id.adAccountId.eq(adAccount.id),
//                                                        adAccountUser.memberType.eq(AdAccountUser.MemberType.MASTER)))),
//                                "masterEmail"),
//                        as(select(Wildcard.count)
//                                        .from(adAccountUser)
//                                        .where(adAccountUser.id.adAccountId.eq(adAccount.id),
//                                                adAccountUser.status.eq(status)),
//                                "memberSize"),
                        adAccount.config,
                        adAccount.adminStop,
                        adAccount.outOfBalance,
                        adAccountUser.status))
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .join(adAccountUser.user, user)
                .where(
                        adAccountUser.id.userId.eq(loginUserNo),
                        adAccountUser.status.eq(status),
                        this.eqId(id),
                        this.containsName(name));

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Page<Response.ForCashSearch> searchForCash(Pageable pageable, Request.ForCashSearch request) {
        List<Response.ForCashSearch> content = this.getSearchForCashQuery(pageable, request)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adAccount.count())
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                ).groupBy(adAccount.id);
        // group by 일 경우 () -> countQuery.fetch().size()
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public List<Response.ForCashSearch> searchForCash(Request.ForCashSearch request) {
        return this.getSearchForCashQuery(null, request).fetch();
    }

    private JPAQuery<Response.ForCashSearch> getSearchForCashQuery(
            Pageable pageable,
            Request.ForCashSearch request) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        JPAQuery<Response.ForCashSearch> query = this.query.select(
                        new QAdAccountDto_Response_ForCashSearch(
                                adAccount.id,
                                adAccount.name,
//                                as(select(user.name)
//                                                .from(user)
//                                                .join(adAccountUser).on(user.id.eq(adAccountUser.id.userId),
//                                                        adAccountUser.memberType.eq(AdAccountUser.MemberType.MASTER))
//                                                .where(adAccountUser.id.adAccountId.eq(adAccount.id)),
//                                        "marketerName"),
//                                company.type,
//                                new QWalletDto_Response_WalletBalance(
//                                        as(select(walletCashTotal.availableAmount.sum().coalesce(Long.valueOf("0")))
//                                                        .from(walletCashTotal)
//                                                        .join(walletCashTotal.cash, cash).on(cash.saleAffect.eq(true))
//                                                        .where(walletCashTotal.id.walletMasterId.eq(adAccount.id)),
//                                                "cash"),
//                                        as(select(walletCashTotal.availableAmount.sum().coalesce(Long.valueOf("0")))
//                                                        .from(walletCashTotal)
//                                                        .join(walletCashTotal.cash, cash).on(cash.saleAffect.eq(false))
//                                                        .where(walletCashTotal.id.walletMasterId.eq(adAccount.id)),
//                                                "freeCash")
//                                ),
                                adAccount.config,
                                adAccount.adminStop,
                                adAccount.outOfBalance
                        )
                )
                .from(adAccount)
                .join(adAccount.adAccountUsers, adAccountUser)
                .where(
                        this.eqId(request.getId()),
                        this.containsName(request.getName())
                )
                .groupBy(
                        adAccount.id
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Optional<Response.AdAccountCount> countStatusYN(Integer loginUserNo) {
        return Optional.ofNullable(this.query.select(new QAdAccountDto_Response_AdAccountCount(
                        as(select(adAccountUser.id.userId.count())
                                        .from(adAccountUser)
                                        .where(adAccountUser.id.userId.eq(adAccountUser.id.userId), adAccountUser.status.eq(AdAccountUser.Status.Y)),
                                "statusYCount"),
                        as(select(adAccountUser.id.userId.count())
                                        .from(adAccountUser)
                                        .where(adAccountUser.id.userId.eq(adAccountUser.id.userId), adAccountUser.status.eq(AdAccountUser.Status.N)),
                                "statusNCount")
                ))
                .from(adAccountUser)
                .where(adAccountUser.id.userId.eq(loginUserNo))
                .groupBy(adAccountUser.id.userId)
                .fetchOne());
    }

    @Override
    public AdAccountDto.Response.AdAccountInfo adAccountInfo(Integer adAccountId) {
        return this.query.select(new QAdAccountDto_Response_AdAccountInfo(
                        adAccount.id,
                        adAccount.name,
                        adAccount.config,
                        adAccount.adminStop,
                        adAccount.outOfBalance
                ))
                .from(adAccount)
                .where(adAccount.id.eq(adAccountId))
                .fetchOne();
    }

//    @Override
//    public AdAccountDto.Response.AdAccountCashInfo adAccountCashInfo(Integer adAccountId) {
//        return this.query.select(new QAdAccountDto_Response_AdAccountCashInfo(
//                        walletCashTotal.amount.sum(),
//                        walletCashTotal.availableAmount.sum(),
//                        walletCashTotal.reserveAmount.sum()
//                ))
//                .from(adAccount, walletCashTotal)
//                .where(adAccount.id.eq(adAccountId),
//                       adAccount.id.eq(walletCashTotal.id.walletMasterId)
//                ).fetchOne();
//    }

//    @Override
//    public List<AdAccountDto.Response.AdAccountCashDetailInfo> adAccountCashDetailInfo(Integer adAccountId) {
//        return this.getAdAccountCashDetailInfoQuery(null, adAccountId).fetch();
//    }

//    private JPAQuery<Response.AdAccountCashDetailInfo> getAdAccountCashDetailInfoQuery(
//            Pageable pageable,
//            Integer adAccountId) {
//        LocalDate now = LocalDate.now();
//        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
//        JPAQuery<Response.AdAccountCashDetailInfo> query = this.query.select(
//                        new QAdAccountDto_Response_AdAccountCashDetailInfo(
//                                Expressions.as(Expressions.constant(adAccountId), "id"),
//                                cash.name,
//                                cash.saleAffect,
//                                cash.refund,
//                                cash.priority,
//                                walletCashTotal.cash.id,
//                                as(walletCashTotal.amount.coalesce(0L), "amount"),
//                                as(walletCashTotal.availableAmount.coalesce(0L), "availableAmount"),
//                                as(walletCashTotal.reserveAmount.coalesce(0L), "reserveAmount")
//                        )
//                )
//                .from(cash)
//                .join(walletCashTotal).on(
//                        walletCashTotal.id.cashId.eq(cash.id),
//                        walletCashTotal.id.walletMasterId.eq(adAccountId)
//                )
//                .where(walletCashTotal.id.cashId.in(Arrays.asList(1, 2))) ;
//
//        return Objects.nonNull(pageable)
//                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdAccount.class, "adAccount", pageable.getSort()).toArray(OrderSpecifier[]::new))
//                : query;
//    }

//    @Override
//    public void outOfBalanceUpdate(Integer adAccountId, Boolean oufOfBalance) {
//        this.query.update(adAccount)
//                .set(adAccount.outOfBalance, oufOfBalance)
//                .where(adAccount.id.eq(adAccountId))
//                .execute();
//    }

    @Override
    public DashboardDto.Response.AdAccountCountByAd adAccountsCountByAd(Integer adAccountId) {
        return this.query.select(new QDashboardDto_Response_AdAccountCountByAd(
                as(select(Wildcard.count)
                        .from(adAccount, campaign)
                        .where(adAccount.id.eq(campaign.adAccount.id),
                                adAccount.id.eq(adAccountId),
                                campaign.config.eq(Campaign.Config.ON),
                                campaign.systemConfig.eq(Campaign.SystemConfig.ON),
                                campaign.status.eq(Campaign.Status.LIVE)),
                        "campaignCount"),
                as(select(Wildcard.count)
                                .from(adAccount, campaign, adGroup)
                                .where(adAccount.id.eq(campaign.adAccount.id),
                                        adAccount.id.eq(adAccountId),
                                        campaign.id.eq(adGroup.campaign.id),
                                        campaign.config.eq(Campaign.Config.ON),
                                        campaign.systemConfig.eq(Campaign.SystemConfig.ON),
                                        campaign.status.eq(Campaign.Status.LIVE),
                                        adGroup.config.eq(AdGroup.Config.ON),
                                        adGroup.systemConfig.eq(AdGroup.SystemConfig.ON),
                                        adGroup.status.eq(AdGroup.Status.LIVE)),
                        "adgroupCount"),
                as(select(Wildcard.count)
                                .from(adAccount, campaign, adGroup, creative)
                                .where(adAccount.id.eq(campaign.adAccount.id),
                                        adAccount.id.eq(adAccountId),
                                        campaign.id.eq(adGroup.campaign.id),
                                        adGroup.id.eq(creative.adGroup.id),
                                        campaign.config.eq(Campaign.Config.ON),
                                        campaign.systemConfig.eq(Campaign.SystemConfig.ON),
                                        campaign.status.eq(Campaign.Status.LIVE),
                                        adGroup.config.eq(AdGroup.Config.ON),
                                        adGroup.systemConfig.eq(AdGroup.SystemConfig.ON),
                                        adGroup.status.eq(AdGroup.Status.LIVE),
                                        creative.config.eq(Creative.Config.ON),
                                        creative.systemConfig.eq(Creative.SystemConfig.ON),
                                        creative.status.eq(Creative.Status.OPERATING)),
                        "creativeCount")
                ))
                .from(adAccount)
                .where(adAccount.id.eq(adAccountId))
                .fetchOne();
    }

    @Override
    public CompanyDto.Response.AdAccountDetail adAccountByAdvertiser(Integer adAccountId) {
        return this.query.select(new QCompanyDto_Response_AdAccountDetail(
                company.id,
                company.name,
                company.type,
                company.registrationNumber,
                company.representationName,
                company.baseAddress,
                company.detailAddress,
                company.zipCode,
                company.businessCategory,
                company.businessItem,
                company.taxBillEmail
                ))
                .from(adAccount, company)
                .where(adAccount.id.eq(adAccountId))
                .fetchOne();
    }

    private BooleanExpression eqId(Integer id) {
        return id != null ? adAccount.id.eq(id) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? adAccount.name.contains(name) : null;
    }
}
