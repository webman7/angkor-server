package com.adplatform.restApi.domain.user.dao;

import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_Detail;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.user.domain.QRole.role;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.adplatform.restApi.domain.user.domain.QUserRole.userRole;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

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
    public Page<UserDto.Response.Detail> search(Pageable pageable) {
        List<UserDto.Response.Detail> content = this.query.from(user)
                .join(user.roles, userRole)
                .join(userRole.role, role)
                .join(user.company, company)
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(User.class, "user", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .transform(groupBy(user.id)
                        .list(new QUserDto_Response_Detail(
                                user.id,
                                user.loginId,
                                user.name,
                                user.email.address,
                                user.phone,
                                user.active,
                                list(userRole.role.value.stringValue()),
                                user.company.name
                        )));

        JPAQuery<Long> countQuery = this.query.select(user.count())
                .from(user)
                .join(user.roles, userRole)
                .join(userRole.role, role)
                .join(user.company, company);


        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
