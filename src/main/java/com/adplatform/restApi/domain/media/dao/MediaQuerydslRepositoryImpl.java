package com.adplatform.restApi.domain.media.dao;

import com.adplatform.restApi.domain.company.dto.QCompanyDto_Response_Default;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.MediaCategory;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.QMediaDto_Response_Search;
import com.adplatform.restApi.domain.media.dto.category.QMediaAndCategoryDto;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroupMedia.adGroupMedia;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.company.domain.QCompanyFile.companyFile;
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.adplatform.restApi.domain.media.domain.QMediaCategory.mediaCategory;
import static com.adplatform.restApi.domain.media.domain.QMediaFile.mediaFile;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.core.types.ExpressionUtils.list;
import static com.querydsl.jpa.JPAExpressions.select;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Repository
public class MediaQuerydslRepositoryImpl implements MediaQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<MediaDto.Response.Search> search(Pageable pageable, MediaDto.Request.Search searchRequest) {
        List<MediaDto.Response.Search> content = this.query.select(
                        new QMediaDto_Response_Search(
                                media.id,
                                media.name,
                                new QCompanyDto_Response_Default(company.id, company.name),
                                media.appKey,
                                media.appSecret,
                                media.url,
                                as(select(mediaFile.information.url)
                                        .from(mediaFile)
                                        .where(mediaFile.media.id.eq(media.id),
                                                mediaFile.id.eq(select(mediaFile.id.max())
                                                        .from(mediaFile)
                                                        .where(mediaFile.media.id.eq(media.id)
                                                        )
                                                        .orderBy(mediaFile.id.desc()))
                                        ), "mediaFileUrl"),
                                media.expInventory,
                                media.memo,
                                media.adminMemo,
                                media.status,
                                user.loginId)
                )
                .from(media, company, user)
                .leftJoin(mediaFile)
                .on(media.id.eq(mediaFile.media.id))
                .where(
                        media.company.id.eq(company.id),
                        media.createdUserNo.eq(user.id),
                        media.status.notIn(Media.Status.D),
                        this.companyEq(searchRequest.getCompanyId()),
                        this.statusEq(searchRequest.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(media.count())
                .from(media)
                .where(media.status.notIn(Media.Status.D),
                        this.statusEq(searchRequest.getStatus())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public String findByMediaIdFileUrl(Integer id) {
        return this.query.select(mediaFile.information.url)
                .from(mediaFile)
                .where(mediaFile.id.eq(select(mediaFile.id.max())
                                .from(mediaFile)
                                .where(mediaFile.media.id.eq(id)
                                )))
                .fetchOne();
    }


    private BooleanExpression statusEq(String status) {
        if(status.equals("N")) {
            return media.status.eq(Media.Status.N);
        } else if(status.equals("R")) {
            return media.status.eq(Media.Status.R);
        } else if(status.equals("Y")) {
            return media.status.eq(Media.Status.Y);
        } else {
            return null;
        }
    }

    private BooleanExpression companyEq(Integer companyId) {
        {
            return nonNull(companyId) && !companyId.equals(0) ? company.id.eq(companyId) : null;
        }
    }








    // 이전 작업
    @Override
    public void deleteByAdGroupId(Integer adGroupId) {
        this.query.delete(adGroupMedia).where(adGroupMedia.id.adGroupId.eq(adGroupId)).execute();
    }
}
