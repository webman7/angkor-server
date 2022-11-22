package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroup.adGroup;
import static java.util.Objects.nonNull;

public class AdGroupCondition {
    public static BooleanExpression inId(List<Integer> ids) {
        return nonNull(ids) && !ids.isEmpty() ? adGroup.id.in(ids) : null;
    }

    public static BooleanExpression containsName(String name) {
        return StringUtils.isNotBlank(name) ? adGroup.name.contains(name) : null;
    }

    public static BooleanExpression inConfig(List<AdGroup.Config> configs) {
        return nonNull(configs) && !configs.isEmpty() ? adGroup.config.in(configs) : null;
    }

    public static BooleanExpression inStatus(List<AdGroup.Status> statuses) {
        return nonNull(statuses) && !statuses.isEmpty() ? adGroup.status.in(statuses) : null;
    }
}
