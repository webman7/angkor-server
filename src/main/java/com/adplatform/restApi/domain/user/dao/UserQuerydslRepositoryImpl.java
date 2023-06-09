package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.business.dto.user.QBusinessAccountUserDto_Response_BusinessAccountUserInfo;
import com.adplatform.restApi.domain.history.domain.UserPasswordChangeHistory;
import com.adplatform.restApi.domain.user.domain.QUser;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_Search;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
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

import static com.adplatform.restApi.domain.business.domain.QBusinessAccount.businessAccount;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccountUser.businessAccountUser;
import static com.adplatform.restApi.domain.history.domain.QUserPasswordChangeHistory.userPasswordChangeHistory;
import static com.adplatform.restApi.domain.statistics.domain.taxbill.QMediaTaxBill.mediaTaxBill;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class UserQuerydslRepositoryImpl implements UserQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return Optional.ofNullable(this.query.selectFrom(user)
                .where(user.loginId.eq(loginId))
                .fetchOne());
    }

    @Override
    public UserDto.Response.BaseInfo findUserByLoginId(String loginId) {
        return this.query.select(new QUserDto_Response_BaseInfo(
                user.id, user.loginId, user.name, user.phone
                ))
                .from(user)
                .where(user.loginId.eq(loginId))
                .fetchOne();
    }

    @Override
    public Optional<UserPasswordChangeHistory> findPasswordConfirm(AuthDto.Request.FindPasswordConfirm request) {
        return Optional.ofNullable(this.query.selectFrom(userPasswordChangeHistory)
                .where(userPasswordChangeHistory.userId.eq(request.getId()),
                        userPasswordChangeHistory.status.eq(UserPasswordChangeHistory.Status.READY))
                .orderBy(userPasswordChangeHistory.id.desc())
                .limit(1)
                .fetchOne());
    }

    @Override
    public Page<UserDto.Response.Search> userSearch(UserDto.Request.Search request, Pageable pageable) {
        List<UserDto.Response.Search> content = this.query.select(new QUserDto_Response_Search(
                        user.id,
                        user.loginId,
                        user.name,
                        user.phone,
                        user.active
                ))
                .from(user)
                .where( this.loginIdContains(request.getUserId()),
                        this.nameContains(request.getName()),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .orderBy(user.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(user.count())
                .from(user)
                .where( this.loginIdContains(request.getUserId()),
                        this.nameContains(request.getName()),
                        user.active.in(User.Active.Y, User.Active.L)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? user.name.contains(name) : null;
    }

    private BooleanExpression loginIdContains(String loginId) {
        return StringUtils.hasText(loginId) ? user.loginId.contains(loginId) : null;
    }

//    @Override
//    public List<Integer> findByUserRoles(Integer id) {
//        return this.query.select(userRole.role.id)
//                .from(user, userRole)
//                .where(user.id.eq(id),
//                        user.id.eq(userRole.user.id))
//                .fetch();
//    }

//    @Override
//    public Page<UserDto.Response.Detail> search(Pageable pageable) {
//        List<UserDto.Response.Detail> content = this.query.select(
//                        new QUserDto_Response_Detail(
//                                user.id,
//                                user.loginId,
//                                user.name,
//                                user.email.address,
//                                user.phone,
//                                user.active,
//                                list(userRole.role.value.stringValue()),
////                                Expressions.stringTemplate("group_concat({0})", list(userRole.role.value.stringValue())),
////                                Expressions.stringTemplate("group_concat({0})", list(userRole.role.value.stringValue())),
////                                        .groupConcat(list(userRole.role.value.stringValue())),
//                                user.company.name
//                        )
//                ).from(user)
//                .join(user.roles, userRole)
//                .join(userRole.role, role)
//                .join(user.company, company)
//                .groupBy(user.id)
//                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(User.class, "user", pageable.getSort()).toArray(OrderSpecifier[]::new))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize());
//
//        JPAQuery<Long> countQuery = this.query.select(user.count())
//                .from(user)
//                .join(user.roles, userRole)
//                .join(userRole.role, role)
//                .join(user.company, company);
//
//
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//    }

//    @Override
//    public Page<UserDto.Response.Detail> search(Pageable pageable) {
//        List<UserDto.Response.Detail> content = this.query.from(user).select()
//                .join(user.roles, userRole)
//                .join(userRole.role, role)
//                .join(user.company, company)
//                .groupBy(user.id)
//                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(User.class, "user", pageable.getSort()).toArray(OrderSpecifier[]::new))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .transform(groupBy(user.id)
//                        .list(new QUserDto_Response_Detail(
//                                user.id,
//                                user.loginId,
//                                user.name,
//                                user.email.address,
//                                user.phone,
//                                user.active,
//                                list(userRole.role.value.stringValue()),
////                                Expressions.stringTemplate("group_concat({0})", list(userRole.role.value.stringValue())),
////                                Expressions.stringTemplate("group_concat({0})", list(userRole.role.value.stringValue())),
////                                        .groupConcat(list(userRole.role.value.stringValue())),
//                                user.company.name
//                        )));
//
//        JPAQuery<Long> countQuery = this.query.select(user.count())
//                .from(user)
//                .join(user.roles, userRole)
//                .join(userRole.role, role)
//                .join(user.company, company);
//
//
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
//    }
}
