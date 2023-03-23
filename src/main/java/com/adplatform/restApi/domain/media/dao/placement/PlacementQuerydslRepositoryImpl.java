package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.media.domain.Placement;
import com.adplatform.restApi.domain.media.dto.placement.*;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroupMedia.adGroupMedia;
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.adplatform.restApi.domain.media.domain.QMediaPlacement.mediaPlacement;
import static com.adplatform.restApi.domain.media.domain.QPlacement.placement;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class PlacementQuerydslRepositoryImpl implements PlacementQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<PlacementDto.Response.Search> search(Pageable pageable) {
        List<PlacementDto.Response.Search> content = this.query.select(
                        new QPlacementDto_Response_Search(
                                placement.id,
                                placement.name,
                                placement.width,
                                placement.height,
                                placement.widthHeightRate,
                                placement.memo,
                                placement.adminMemo,
                                placement.status,
                                user.loginId,
                                placement.createdAt)
                )
                .from(placement, user)
                .where(placement.status.in(Placement.Status.Y, Placement.Status.N),
                        placement.createdUserNo.eq(user.id)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(placement.count())
                .from(placement)
                .where(placement.status.in(Placement.Status.Y, Placement.Status.N)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<PlacementDto.Response.Search> list() {
        return this.getListQuery(null).fetch();
    }

    private JPAQuery<PlacementDto.Response.Search> getListQuery(
            Pageable pageable) {

        JPAQuery<PlacementDto.Response.Search> query = this.query.select(
                new QPlacementDto_Response_Search(
                        placement.id,
                        placement.name,
                        placement.width,
                        placement.height,
                        placement.widthHeightRate,
                        placement.memo,
                        placement.adminMemo,
                        placement.status,
                        user.loginId,
                        placement.createdAt))
                .from(placement, user)
                .where(placement.status.in(Placement.Status.Y),
                        placement.createdUserNo.eq(user.id)
                );

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Placement.class, "placement", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    @Override
    public Integer findByWidthAndHeight(Integer width, Integer height) {
        List<PlacementDto.Response.Default> content = this.query.select(
                        new QPlacementDto_Response_Default(
                                placement.id)
                )
                .from(placement)
                .where(
                        placement.width.eq(width),
                        placement.height.eq(height),
                        placement.status.in(Placement.Status.Y)
                )
                .fetch();

        return content.size();
    }














//    @Override
//    public List<PlacementDto.Response.ForSearchAll> searchForAll(Integer adGroupId, Integer mediaId) {
//        return this.getSearchForAllQuery(null, adGroupId, mediaId).fetch();
//    }

//    private JPAQuery<PlacementDto.Response.ForSearchAll> getSearchForAllQuery(
//            Pageable pageable, Integer adGroupId, Integer mediaId) {
//
//        JPAQuery<PlacementDto.Response.ForSearchAll> query = this.query.select(new QPlacementDto_Response_ForSearchAll(
//                        placement.id,
//                        placement.name,
//                        placement.width,
//                        placement.height,
//                        placement.widthHeightRate,
//                        placement.memo,
//                        placement.adminMemo))
//                .from(placement, media, adGroupMedia)
//                .where(
//                        media.id.eq(adGroupMedia.media.id),
//                        this.eqAdGroupId(adGroupId),
//                        this.eqMediaId(mediaId));
//
//        return Objects.nonNull(pageable)
//                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Placement.class, "placement", pageable.getSort()).toArray(OrderSpecifier[]::new))
//                : query;
//    }

    private BooleanExpression eqAdGroupId(Integer id) {
        return id != null ? adGroupMedia.adGroup.id.eq(id) : null;
    }

    private BooleanExpression eqMediaId(Integer id) {
        return id != null ? media.id.eq(id) : null;
    }
}
