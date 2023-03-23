package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.company.dto.QCompanyDto_Response_Default;
import com.adplatform.restApi.domain.media.dao.category.MediaCategoryQuerydslRepository;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.MediaPlacement;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.QMediaDto_Response_Search;
import com.adplatform.restApi.domain.media.dto.placement.MediaPlacementDto;
import com.adplatform.restApi.domain.media.dto.placement.QMediaPlacementDto_Response_Default;
import com.adplatform.restApi.domain.media.dto.placement.QMediaPlacementDto_Response_Search;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.adplatform.restApi.domain.media.domain.QMediaPlacement.mediaPlacement;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Repository
public class MediaPlacementQuerydslRepositoryImpl implements MediaPlacementQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<MediaPlacementDto.Response.Search> search(Pageable pageable, MediaPlacementDto.Request.Search searchRequest) {
        List<MediaPlacementDto.Response.Search> content = this.query.select(
                        new QMediaPlacementDto_Response_Search(
                                mediaPlacement.id,
                                company.name,
                                media.name,
                                mediaPlacement.name,
                                mediaPlacement.width,
                                mediaPlacement.height,
                                mediaPlacement.widthHeightRate,
                                mediaPlacement.url,
                                mediaPlacement.memo,
                                mediaPlacement.adminMemo,
                                mediaPlacement.status,
                                user.loginId,
                                mediaPlacement.createdAt)
                )
                .from(media, company, user, mediaPlacement)
                .where(
                        media.company.id.eq(company.id),
                        mediaPlacement.createdUserNo.eq(user.id),
                        mediaPlacement.media.id.eq(media.id),
                        mediaPlacement.status.notIn(MediaPlacement.Status.D),
                        media.status.notIn(Media.Status.D),
                        this.companyEq(searchRequest.getCompanyId()),
                        this.mediaEq(searchRequest.getMediaId()),
                        this.statusEq(searchRequest.getStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(media.count())
                .from(media, company, user, mediaPlacement)
                .where(
                        media.company.id.eq(company.id),
                        mediaPlacement.createdUserNo.eq(user.id),
                        mediaPlacement.media.id.eq(media.id),
                        mediaPlacement.status.notIn(MediaPlacement.Status.D),
                        media.status.notIn(Media.Status.D),
                        this.companyEq(searchRequest.getCompanyId()),
                        this.mediaEq(searchRequest.getMediaId()),
                        this.statusEq(searchRequest.getStatus())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
    @Override
    public Integer findByWidthAndHeight(Integer width, Integer height) {
        List<MediaPlacementDto.Response.Default> content = this.query.select(
                        new QMediaPlacementDto_Response_Default(
                                mediaPlacement.id)
                )
                .from(mediaPlacement)
                .where(
                        mediaPlacement.width.eq(width),
                        mediaPlacement.height.eq(height)
                )
                .fetch();

        return content.size();
    }

    private BooleanExpression statusEq(String status) {
        if(status.equals("N")) {
            return mediaPlacement.status.eq(MediaPlacement.Status.N);
        } else if(status.equals("R")) {
            return mediaPlacement.status.eq(MediaPlacement.Status.R);
        } else if(status.equals("Y")) {
            return mediaPlacement.status.eq(MediaPlacement.Status.Y);
        } else {
            return null;
        }
    }

    private BooleanExpression companyEq(Integer companyId) {
        {
            return nonNull(companyId) && !companyId.equals(0) ? company.id.eq(companyId) : null;
        }
    }

    private BooleanExpression mediaEq(Integer mediaId) {
        {
            return nonNull(mediaId) && !mediaId.equals(0) ? media.id.eq(mediaId) : null;
        }
    }
}
