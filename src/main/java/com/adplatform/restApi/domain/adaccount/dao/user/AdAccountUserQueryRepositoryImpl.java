package com.adplatform.restApi.domain.adaccount.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.user.AdAccountUserDto;
import com.adplatform.restApi.domain.adaccount.dto.user.QAdAccountUserDto_Response_AdAccountUserInfo;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class AdAccountUserQueryRepositoryImpl implements AdAccountUserQueryRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<AdAccountUser> findByAdAccountIdAndUserId(Integer adAccountId, Integer userId) {
        return Optional.ofNullable(this.query.selectFrom(adAccountUser)
                .where(adAccountUser.id.adAccountId.eq(adAccountId), adAccountUser.id.userId.eq(userId))
                .fetchOne());
    }

    @Override
    public Integer findByAdAccountIdAndUserIdCount(Integer adAccountId, Integer userId) {
        List<Integer> content = this.query.select(
                        adAccountUser.id.adAccountId
                )
                .from(adAccountUser)
                .where(adAccountUser.id.adAccountId.eq(adAccountId), adAccountUser.id.userId.eq(userId))
                .fetch();

        return content.size();
    }


    @Override
    public Page<AdAccountUserDto.Response.AdAccountUserInfo> adAccountUserInfo(Integer adAccountId, Pageable pageable) {
        List<AdAccountUserDto.Response.AdAccountUserInfo> content = this.query.select(new QAdAccountUserDto_Response_AdAccountUserInfo(
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
                .from(adAccount, adAccountUser, user)
                .where(adAccount.id.eq(adAccountId),
                        adAccountUser.adAccount.id.eq(adAccount.id),
                        adAccountUser.user.id.eq(user.id),
                        adAccountUser.status.in(AdAccountUser.Status.Y),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adAccountUser.count())
                .from(adAccount, adAccountUser, user)
                .where(adAccount.id.eq(adAccountId),
                        adAccountUser.adAccount.id.eq(adAccount.id),
                        adAccountUser.user.id.eq(user.id),
                        adAccountUser.status.in(AdAccountUser.Status.Y),
                        user.active.in(User.Active.Y, User.Active.L)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<AdAccountUserDto.Response.AdAccountUserInfo> adAccountRequestUserInfo(Integer adAccountId) {
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
                .from(adAccount, adAccountUser, user)
                .where(adAccount.id.eq(adAccountId),
                        adAccountUser.adAccount.id.eq(adAccount.id),
                        adAccountUser.user.id.eq(user.id),
                        adAccountUser.status.in(AdAccountUser.Status.N, AdAccountUser.Status.R, AdAccountUser.Status.C),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetch();
    }

    @Override
    public AdAccountUserDto.Response.AdAccountUserInfo adAccountUserInfo(Integer adAccountId, Integer userNo) {
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
                .from(adAccount, adAccountUser, user)
                .where(adAccount.id.eq(adAccountId),
                        user.id.eq(userNo),
                        adAccountUser.adAccount.id.eq(adAccount.id),
                        adAccountUser.user.id.eq(user.id),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetchOne();
    }

    @Override
    public void deleteByAdAccountIdAndUserId(Integer adAccountId, Integer userId) {
        this.query.delete(adAccountUser)
                .where(adAccountUser.id.adAccountId.eq(adAccountId), adAccountUser.id.userId.eq(userId))
                .execute();
    }
}
