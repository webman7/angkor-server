package com.adplatform.restApi.domain.company.dao.user;

import com.adplatform.restApi.domain.company.domain.AdminUser;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.user.*;
import com.adplatform.restApi.domain.company.dto.user.AdminUserDto;
import com.adplatform.restApi.domain.company.dto.user.QAdminUserDto_Response_AdminUserInfo;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
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

import static com.adplatform.restApi.domain.company.domain.QAdminUser.adminUser;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.company.domain.QMediaCompanyUser.mediaCompanyUser;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class AdminUserQuerydslRepositoryImpl implements AdminUserQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<AdminUser> findByCompanyIdAndUserId(Integer companyId, Integer userId) {
        return Optional.ofNullable(this.query.selectFrom(adminUser)
                .where(adminUser.id.companyId.eq(companyId), adminUser.id.userId.eq(userId))
                .fetchOne());
    }

    @Override
    public Integer findByCompanyIdCount(Integer companyId) {
        List<Integer> content = this.query.select(
                        adminUser.id.companyId
                )
                .from(adminUser)
                .where(adminUser.id.companyId.eq(companyId))
                .fetch();

        return content.size();
    }

    @Override
    public Integer findByCompanyIdAndUserIdCount(Integer companyId, Integer userId) {
        List<Integer> content = this.query.select(
                        adminUser.id.companyId
                )
                .from(adminUser)
                .where(adminUser.id.companyId.eq(companyId), adminUser.id.userId.eq(userId))
                .fetch();

        return content.size();
    }

    @Override
    public void deleteByCompanyIdAndUserIdCount(Integer companyId, Integer userId) {
        this.query.delete(adminUser)
                .where(adminUser.id.companyId.eq(companyId), adminUser.id.userId.eq(userId))
                .execute();
    }

    @Override
    public AdminUserDto.Response.AdminUserInfo adminUserInfo(Integer companyId, Integer userNo) {
        return this.query.select(new QAdminUserDto_Response_AdminUserInfo(
                        company.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        adminUser.memberType,
                        adminUser.status
                ))
                .from(company, adminUser, user)
                .where(company.id.eq(companyId),
                        user.id.eq(userNo),
                        company.type.eq(Company.Type.ADMIN),
                        adminUser.company.id.eq(company.id),
                        adminUser.user.id.eq(user.id),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetchOne();
    }

    @Override
    public AdminUserDto.Response.AdminUserInfo adminUserInfo(Integer userNo) {
        return this.query.select(new QAdminUserDto_Response_AdminUserInfo(
                        company.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        adminUser.memberType,
                        adminUser.status
                ))
                .from(company, adminUser, user)
                .where( user.id.eq(userNo),
                        company.type.eq(Company.Type.ADMIN),
                        adminUser.company.id.eq(company.id),
                        adminUser.user.id.eq(user.id),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetchOne();
    }

    @Override
    public Page<AdminUserDto.Response.AdminUserInfo> adminUserSearch(Pageable pageable, Integer companyId, AdminUserDto.Request.SearchAdminUser searchRequest) {
        List<AdminUserDto.Response.AdminUserInfo> content = this.query.select(new QAdminUserDto_Response_AdminUserInfo(
                        company.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        adminUser.memberType,
                        adminUser.status
                ))
                .from(company, adminUser, user)
                .where(company.id.eq(companyId),
                        this.loginIdContains(searchRequest.getUserId()),
                        this.nameContains(searchRequest.getName()),
                        company.type.eq(Company.Type.ADMIN),
                        adminUser.company.id.eq(company.id),
                        adminUser.user.id.eq(user.id),
                        adminUser.status.notIn(AdminUser.Status.D),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(adminUser.count())
                .from(company, adminUser, user)
                .where(company.id.eq(companyId),
                        this.loginIdContains(searchRequest.getUserId()),
                        this.nameContains(searchRequest.getName()),
                        company.type.eq(Company.Type.ADMIN),
                        adminUser.company.id.eq(company.id),
                        adminUser.user.id.eq(user.id),
                        adminUser.status.notIn(AdminUser.Status.D),
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
}
