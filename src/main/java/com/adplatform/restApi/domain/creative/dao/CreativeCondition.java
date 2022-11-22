package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.Creative;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.adplatform.restApi.domain.creative.domain.QCreative.creative;
import static java.util.Objects.nonNull;

public class CreativeCondition {
    public static BooleanExpression inId(List<Integer> ids) {
        return nonNull(ids) && !ids.isEmpty() ? creative.id.in(ids) : null ;
    }

    public static BooleanExpression containsName(String name) {
        return StringUtils.isNotBlank(name) ? creative.name.contains(name) : null;
    }

    public static BooleanExpression inFormat(List<Creative.Format> formats) {
        return nonNull(formats) && !formats.isEmpty() ? creative.format.in(formats) : null;
    }

    public static BooleanExpression inConfig(List<Creative.Config> configs) {
        return nonNull(configs) && !configs.isEmpty() ? creative.config.in(configs) : null;
    }

    public static BooleanExpression inStatus(List<Creative.Status> statuses) {
        return nonNull(statuses) && !statuses.isEmpty() ? creative.status.in(statuses) : null;
    }

    public static BooleanExpression inReviewStatus(List<Creative.ReviewStatus> reviewStatuses) {
        return nonNull(reviewStatuses) && !reviewStatuses.isEmpty() ? creative.reviewStatus.in(reviewStatuses) : null;
    }
}
