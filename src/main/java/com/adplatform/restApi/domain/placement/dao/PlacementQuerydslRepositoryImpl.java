package com.adplatform.restApi.domain.placement.dao;

import com.adplatform.restApi.domain.placement.domain.Placement;
import com.adplatform.restApi.domain.placement.dto.placement.PlacementDto;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.adplatform.restApi.domain.placement.dto.placement.QPlacementDto_Response_ForSearchAll;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QAdGroupMedia.adGroupMedia;
import static com.adplatform.restApi.domain.advertiser.adgroup.domain.QMedia.media;
import static com.adplatform.restApi.domain.placement.domain.QPlacement.placement;

@RequiredArgsConstructor
@Repository
public class PlacementQuerydslRepositoryImpl implements PlacementQuerydslRepository {

    private final JPAQueryFactory query;

//    @Override
    public List<PlacementDto.Response.ForSearchAll> searchForAll(Integer adGroupId, Integer mediaId) {
        return this.getSearchForAllQuery(null, adGroupId, mediaId).fetch();
    }

    private JPAQuery<PlacementDto.Response.ForSearchAll> getSearchForAllQuery(
            Pageable pageable, Integer adGroupId, Integer mediaId) {

        JPAQuery<PlacementDto.Response.ForSearchAll> query = this.query.select(new QPlacementDto_Response_ForSearchAll(
                        placement.id,
                        placement.media.id,
                        placement.name,
                        placement.width,
                        placement.height))
                .from(placement, media, adGroupMedia)
                .where(
                        placement.media.id.eq(media.id),
                        media.id.eq(adGroupMedia.media.id),
                        this.eqAdGroupId(adGroupId),
                        this.eqMediaId(mediaId))
                .groupBy(placement.id, placement.media.id);

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Placement.class, "placement", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    private BooleanExpression eqAdGroupId(Integer id) {
        return id != null ? adGroupMedia.adGroup.id.eq(id) : null;
    }

    private BooleanExpression eqMediaId(Integer id) {
        return id != null ? media.id.eq(id) : null;
    }
}
