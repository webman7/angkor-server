package com.adplatform.restApi.domain.media.dao;

import com.adplatform.restApi.domain.company.dto.QCompanyDto_Response_Default;
import com.adplatform.restApi.domain.media.domain.FileInformation;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroupMedia.adGroupMedia;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.adplatform.restApi.domain.media.domain.QMediaFile.mediaFile;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.querydsl.core.types.ExpressionUtils.*;
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
                                                        ))
                                        ), "mediaFileUrl"),
                                as(select(mediaFile.information.originalFileName)
                                        .from(mediaFile)
                                        .where(mediaFile.media.id.eq(media.id),
                                                mediaFile.id.eq(select(mediaFile.id.max())
                                                        .from(mediaFile)
                                                        .where(mediaFile.media.id.eq(media.id)
                                                        ))
                                        ), "mediaFileName"),
                                as(select(mediaFile.information.fileType)
                                        .from(mediaFile)
                                        .where(mediaFile.media.id.eq(media.id),
                                                mediaFile.id.eq(select(mediaFile.id.max())
                                                        .from(mediaFile)
                                                        .where(mediaFile.media.id.eq(media.id)
                                                        ))
                                        ), "mediaFileType"),
                                media.expInventory,
                                media.memo,
                                media.adminMemo,
                                media.status,
                                user.loginId,
                                media.createdAt)
                )
                .from(media, company, user)
                .leftJoin(mediaFile)
                .on(mediaFile.media.id.eq(media.id),
                        mediaFile.id.eq(select(mediaFile.id.max())
                                .from(mediaFile)
                                .where(mediaFile.media.id.eq(media.id)
                                )))
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
                .from(media, company, user)
                .leftJoin(mediaFile)
                .on(mediaFile.media.id.eq(media.id),
                        mediaFile.id.eq(select(mediaFile.id.max())
                                .from(mediaFile)
                                .where(mediaFile.media.id.eq(media.id)
                                )))
                .where(
                        media.company.id.eq(company.id),
                        media.createdUserNo.eq(user.id),
                        media.status.notIn(Media.Status.D),
                        this.companyEq(searchRequest.getCompanyId()),
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

    @Override
    public FileInformation.FileType findByMediaIdFileType(Integer id) {
        return this.query.select(mediaFile.information.fileType)
                .from(mediaFile)
                .where(mediaFile.id.eq(select(mediaFile.id.max())
                        .from(mediaFile)
                        .where(mediaFile.media.id.eq(id)
                        )))
                .fetchOne();
    }

    @Override
    public Integer findByMediaIdFileId(Integer id) {
        return this.query.select(mediaFile.id)
                .from(mediaFile)
                .where(mediaFile.id.eq(select(mediaFile.id.max())
                        .from(mediaFile)
                        .where(mediaFile.media.id.eq(id)
                        )))
                .fetchOne();
    }

    @Override
    public MediaFileDto.Response.FileInfo findByMediaIdFileInfo(Integer id) {
        return this.query.select(new QMediaFileDto_Response_FileInfo(
                mediaFile.information.fileType,
                mediaFile.information.fileSize,
                mediaFile.information.filename,
                mediaFile.information.originalFileName,
                mediaFile.information.url,
                mediaFile.information.mimeType
                ))
                .from(mediaFile)
                .where(mediaFile.id.eq(select(mediaFile.id.max())
                        .from(mediaFile)
                        .where(mediaFile.media.id.eq(id)
                        )))
                .fetchOne();
    }

    @Override
    public List<MediaDto.Response.Default> mediaAll() {
        return this.query.select(new QMediaDto_Response_Default(
                        media.id,
                        media.name
                ))
                .from(media)
                .where(media.status.eq(Media.Status.Y))
                .fetch();
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

    @Override
    public void deleteByAdGroupId(Integer adGroupId) {
        this.query.delete(adGroupMedia).where(adGroupMedia.id.adGroupId.eq(adGroupId)).execute();
    }
}
