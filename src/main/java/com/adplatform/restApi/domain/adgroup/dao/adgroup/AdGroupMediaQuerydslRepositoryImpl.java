package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroupMedia;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupMediaDto;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.QAdGroupMediaDto_Response_Default;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupMedia.adGroupMedia;

@RequiredArgsConstructor
@Repository
public class AdGroupMediaQuerydslRepositoryImpl implements AdGroupMediaQuerydslRepository {

    private final JPAQueryFactory query;

    public List<AdGroupMediaDto.Response.Default> toAdGroupMedia(Integer adGroupId) {
        return this.getAdGroupMediaById(null, adGroupId).fetch();
    }

    private JPAQuery<AdGroupMediaDto.Response.Default> getAdGroupMediaById(
            Pageable pageable, Integer adGroupId) {

        JPAQuery<AdGroupMediaDto.Response.Default> query = this.query.select(new QAdGroupMediaDto_Response_Default(
                        adGroupMedia.adGroup.id,
                        adGroupMedia.media.id))
                .from(adGroupMedia)
                .where(
                        this.eqAdGroupId(adGroupId));

        return Objects.nonNull(pageable)
                ? query.orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(AdGroupMedia.class, "adgroup_media", pageable.getSort()).toArray(OrderSpecifier[]::new))
                : query;
    }

    private BooleanExpression eqAdGroupId(Integer id) {
        return id != null ? adGroupMedia.adGroup.id.eq(id) : null;
    }
}
