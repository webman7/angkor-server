package com.adplatform.restApi.user.dao;

import com.adplatform.restApi.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.user.domain.QRole.role;
import static com.adplatform.restApi.user.domain.QUser.user;
import static com.adplatform.restApi.user.domain.QUserRole.userRole;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class UserQuerydslRepositoryImpl implements UserQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return Optional.ofNullable(this.query.selectFrom(user)
                .join(user.roles, userRole).fetchJoin()
                .join(userRole.role, role).fetchJoin()
                .where(user.loginId.eq(loginId))
                .fetchOne());
    }

    @Override
    public List<Integer> findByUserRoles(Integer id) {
        return this.query.select(userRole.role.id)
                .from(user, userRole)
                .where(user.id.eq(id),
                        user.id.eq(userRole.user.id))
                .fetch();
    }

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
