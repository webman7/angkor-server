package com.adplatform.restApi.domain.company.dao.user;

import com.adplatform.restApi.domain.company.dto.user.AdminUserDto;
import com.adplatform.restApi.domain.company.dto.user.QAdminUserDto_Response_AdminUserInfo;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.company.domain.QAdminUser.adminUser;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class AdminUserQuerydslRepositoryImpl implements AdminUserQuerydslRepository {

    private final JPAQueryFactory query;
    
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
                        adminUser.company.id.eq(company.id),
                        adminUser.user.id.eq(user.id),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetchOne();
    }
}
