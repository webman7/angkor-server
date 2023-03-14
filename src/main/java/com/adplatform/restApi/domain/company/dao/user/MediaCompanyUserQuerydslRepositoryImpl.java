package com.adplatform.restApi.domain.company.dao.user;

import com.adplatform.restApi.domain.company.domain.MediaCompanyUser;
import com.adplatform.restApi.domain.company.dto.user.MediaCompanyUserDto;
import com.adplatform.restApi.domain.company.dto.user.QMediaCompanyUserDto_Response_MediaCompanyUserInfo;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.QUserDto_Response_BaseInfo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.company.domain.QMediaCompanyUser.mediaCompanyUser;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class MediaCompanyUserQuerydslRepositoryImpl implements MediaCompanyUserQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<MediaCompanyUser> findByCompanyIdAndUserId(Integer companyId, Integer userId) {
        return Optional.ofNullable(this.query.selectFrom(mediaCompanyUser)
                .where(mediaCompanyUser.id.companyId.eq(companyId), mediaCompanyUser.id.userId.eq(userId))
                .fetchOne());
    }

    @Override
    public Integer findByCompanyIdCount(Integer companyId) {
        List<Integer> content = this.query.select(
                        mediaCompanyUser.id.companyId
                )
                .from(mediaCompanyUser)
                .where(mediaCompanyUser.id.companyId.eq(companyId))
                .fetch();

        return content.size();
    }

    @Override
    public Integer findByCompanyIdAndUserIdCount(Integer companyId, Integer userId) {
        List<Integer> content = this.query.select(
                        mediaCompanyUser.id.companyId
                )
                .from(mediaCompanyUser)
                .where(mediaCompanyUser.id.companyId.eq(companyId), mediaCompanyUser.id.userId.eq(userId))
                .fetch();

        return content.size();
    }


    @Override
    public List<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyUserInfo(Integer companyId) {
        return this.query.select(new QMediaCompanyUserDto_Response_MediaCompanyUserInfo(
                        company.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        mediaCompanyUser.memberType,
                        mediaCompanyUser.accountingYN,
                        mediaCompanyUser.status
                ))
                .from(company, mediaCompanyUser, user)
                .where(company.id.eq(companyId),
                        mediaCompanyUser.company.id.eq(company.id),
                        mediaCompanyUser.user.id.eq(user.id),
                        mediaCompanyUser.status.in(MediaCompanyUser.Status.Y),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetch();
    }

    @Override
    public List<MediaCompanyUserDto.Response.MediaCompanyUserInfo> mediaCompanyRequestUserInfo(Integer companyId) {
        return this.query.select(new QMediaCompanyUserDto_Response_MediaCompanyUserInfo(
                        company.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        mediaCompanyUser.memberType,
                        mediaCompanyUser.accountingYN,
                        mediaCompanyUser.status
                ))
                .from(company, mediaCompanyUser, user)
                .where(company.id.eq(companyId),
                        mediaCompanyUser.company.id.eq(company.id),
                        mediaCompanyUser.user.id.eq(user.id),
                        mediaCompanyUser.status.in(MediaCompanyUser.Status.N, MediaCompanyUser.Status.R, MediaCompanyUser.Status.C),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetch();
    }

    @Override
    public MediaCompanyUserDto.Response.MediaCompanyUserInfo mediaCompanyUserInfo(Integer companyId, Integer userNo) {
        return this.query.select(new QMediaCompanyUserDto_Response_MediaCompanyUserInfo(
                        company.id,
                        new QUserDto_Response_BaseInfo(
                                user.id,
                                user.loginId,
                                user.name,
                                user.phone
                        ),
                        mediaCompanyUser.memberType,
                        mediaCompanyUser.accountingYN,
                        mediaCompanyUser.status
                ))
                .from(company, mediaCompanyUser, user)
                .where(company.id.eq(companyId),
                        user.id.eq(userNo),
                        mediaCompanyUser.company.id.eq(company.id),
                        mediaCompanyUser.user.id.eq(user.id),
                        user.active.in(User.Active.Y, User.Active.L)
                )
                .fetchOne();
    }

    @Override
    public void deleteByCompanyIdAndUserIdCount(Integer companyId, Integer userId) {
        this.query.delete(mediaCompanyUser)
                .where(mediaCompanyUser.id.companyId.eq(companyId), mediaCompanyUser.id.userId.eq(userId))
                .execute();
    }

    @Override
    public void updateAccounting(Integer companyId, Integer userId, MediaCompanyUser.AccountingYN accountingYN) {
        this.query.update(mediaCompanyUser)
                .set(mediaCompanyUser.accountingYN, accountingYN)
                .where(mediaCompanyUser.id.companyId.eq(companyId), mediaCompanyUser.id.userId.eq(userId))
                .execute();
    }
}
