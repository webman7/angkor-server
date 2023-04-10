package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.adaccount.dto.user.QAdAccountUserDto_Response_AdAccountUserInfo;
import com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup.AdGroupCondition;
import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.business.dto.user.QBusinessAccountUserDto_Response_BusinessAccountUserInfo;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.advertiser.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccount.businessAccount;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccountUser.businessAccountUser;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class BusinessAccountUserQueryRepositoryImpl implements BusinessAccountUserQueryRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<BusinessAccountUser> findByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId) {
        return Optional.ofNullable(this.query.selectFrom(businessAccountUser)
                .where(businessAccountUser.id.businessAccountId.eq(businessAccountId), businessAccountUser.id.userId.eq(userId))
                .fetchOne());
    }

    @Override
    public Integer findByBusinessAccountIdAndUserIdCount(Integer businessAccountId, Integer userId) {
        List<Integer> content = this.query.select(
                        businessAccountUser.id.businessAccountId
                )
                .from(businessAccountUser)
                .where(businessAccountUser.id.businessAccountId.eq(businessAccountId), businessAccountUser.id.userId.eq(userId))
                .fetch();

        return content.size();
    }

    @Override
    public Page<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountUserInfo(Integer businessAccountId, Pageable pageable) {
        List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> content = this.query.select(new QBusinessAccountUserDto_Response_BusinessAccountUserInfo(
                        businessAccount.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        businessAccountUser.memberType,
                        businessAccountUser.accountingYN,
                        businessAccountUser.status
                ))
                .from(businessAccount, businessAccountUser, user)
                .where(businessAccount.id.eq(businessAccountId),
                        businessAccountUser.businessAccount.id.eq(businessAccount.id),
                        businessAccountUser.user.id.eq(user.id),
                        businessAccountUser.status.in(BusinessAccountUser.Status.Y),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(businessAccountUser.count())
                .from(businessAccount, businessAccountUser, user)
                .where(businessAccount.id.eq(businessAccountId),
                        businessAccountUser.businessAccount.id.eq(businessAccount.id),
                        businessAccountUser.user.id.eq(user.id),
                        businessAccountUser.status.in(BusinessAccountUser.Status.Y),
                        user.active.in(User.Active.Y, User.Active.L)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountUserSearch(Integer businessAccountId, BusinessAccountDto.Request.SearchUser searchRequest, Pageable pageable) {
        List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> content = this.query.select(new QBusinessAccountUserDto_Response_BusinessAccountUserInfo(
                        businessAccount.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        businessAccountUser.memberType,
                        businessAccountUser.accountingYN,
                        businessAccountUser.status
                ))
                .from(businessAccount, businessAccountUser, user)
                .where(businessAccount.id.eq(businessAccountId),
                        this.loginIdContains(searchRequest.getUserId()),
                        this.nameContains(searchRequest.getName()),
                        businessAccountUser.businessAccount.id.eq(businessAccount.id),
                        businessAccountUser.user.id.eq(user.id),
                        businessAccountUser.status.notIn(BusinessAccountUser.Status.D),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(businessAccountUser.count())
                .from(businessAccount, businessAccountUser, user)
                .where(businessAccount.id.eq(businessAccountId),
                        businessAccountUser.businessAccount.id.eq(businessAccount.id),
                        businessAccountUser.user.id.eq(user.id),
                        businessAccountUser.status.notIn(BusinessAccountUser.Status.D),
                        user.active.in(User.Active.Y, User.Active.L)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<BusinessAccountUserDto.Response.BusinessAccountUserInfo> businessAccountRequestUserInfo(Integer businessAccountId) {
        return this.query.select(new QBusinessAccountUserDto_Response_BusinessAccountUserInfo(
                        businessAccount.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        businessAccountUser.memberType,
                        businessAccountUser.accountingYN,
                        businessAccountUser.status
                ))
                .from(businessAccount, businessAccountUser, user)
                .where(businessAccount.id.eq(businessAccountId),
                        businessAccountUser.businessAccount.id.eq(businessAccount.id),
                        businessAccountUser.user.id.eq(user.id),
                        businessAccountUser.status.in(BusinessAccountUser.Status.N, BusinessAccountUser.Status.R, BusinessAccountUser.Status.C),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetch();
    }

    @Override
    public BusinessAccountUserDto.Response.BusinessAccountUserInfo businessAccountUserInfo(Integer businessAccountId, Integer userNo) {
        return this.query.select(new QBusinessAccountUserDto_Response_BusinessAccountUserInfo(
                        businessAccount.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        businessAccountUser.memberType,
                        businessAccountUser.accountingYN,
                        businessAccountUser.status
                ))
                .from(businessAccount, businessAccountUser, user)
                .where(businessAccount.id.eq(businessAccountId),
                        user.id.eq(userNo),
                        businessAccountUser.businessAccount.id.eq(businessAccount.id),
                        businessAccountUser.user.id.eq(user.id),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetchOne();
    }

    @Override
    public void deleteByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId) {
        this.query.delete(businessAccountUser)
                .where(businessAccountUser.id.businessAccountId.eq(businessAccountId), businessAccountUser.id.userId.eq(userId))
                .execute();
    }

    @Override
    public void updateAccounting(Integer businessAccountId, Integer userId, BusinessAccountUser.AccountingYN accountingYN) {
        this.query.update(businessAccountUser)
                .set(businessAccountUser.accountingYN, accountingYN)
                .where(businessAccountUser.id.businessAccountId.eq(businessAccountId), businessAccountUser.id.userId.eq(userId))
                .execute();
    }

    @Override
    public List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId) {
        return this.query.select(new QAdAccountUserDto_Response_AdAccountUserInfo(
                adAccount.id,
                new QUserDto_Response_BaseInfo(
                        user.id,
                        user.loginId,
                        user.name,
                        user.phone
                ),
                adAccountUser.memberType,
                adAccountUser.status
                ))
                .from(businessAccount, adAccount, adAccountUser)
                .where(
                        businessAccount.id.eq(adAccount.businessAccount.id),
                        adAccount.id.eq(adAccountUser.id.adAccountId),
                        businessAccount.id.eq(businessAccountId),
                        adAccountUser.id.userId.eq(userId)
                ).fetch();
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? user.name.contains(name) : null;
    }

    private BooleanExpression loginIdContains(String loginId) {
        return StringUtils.hasText(loginId) ? user.loginId.contains(loginId) : null;
    }
}
