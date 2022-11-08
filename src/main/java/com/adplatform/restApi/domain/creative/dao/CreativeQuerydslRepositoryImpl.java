package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.Creative;
import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import com.adplatform.restApi.domain.creative.dto.QCreativeDto_Response_Default;
import com.adplatform.restApi.global.util.QuerydslOrderSpecifierUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroup.adGroup;
import static com.adplatform.restApi.domain.creative.domain.QCreative.creative;
import static com.adplatform.restApi.domain.creative.domain.QCreativeFile.creativeFile;

@RequiredArgsConstructor
@Repository
public class CreativeQuerydslRepositoryImpl implements CreativeQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<CreativeDto.Response.Default> search(Pageable pageable, String name) {
        List<CreativeDto.Response.Default> content = this.query.select(new QCreativeDto_Response_Default(
                        creative.id,
                        creative.name,
                        creative.config,
                        creative.systemConfig,
                        creative.reviewStatus,
                        creative.status,
                        creative.adGroup.id,
                        adGroup.name,
                        creativeFile.information.filename))
                .from(creative)
                .join(creative.adGroup, adGroup)
                .join(creative.files, creativeFile)
                .where(this.containsName(name))
                .orderBy(QuerydslOrderSpecifierUtil.getOrderSpecifier(Creative.class, "creative", pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(creative.count())
                .from(creative)
                .join(creative.adGroup, adGroup)
                .join(creative.files, creativeFile)
                .where(this.containsName(name));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.isNotBlank(name) ? creative.name.contains(name) : null;
    }
}
