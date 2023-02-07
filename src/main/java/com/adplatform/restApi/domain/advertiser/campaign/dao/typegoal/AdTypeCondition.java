package com.adplatform.restApi.domain.advertiser.campaign.dao.typegoal;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.advertiser.campaign.domain.QAdType.adType;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdTypeCondition {
    public static BooleanExpression eqName(String name) {
        return StringUtils.isNotBlank(name) ? adType.name.eq(name) : null;
    }

    public static BooleanExpression inName(List<String> names) {
        return Objects.nonNull(names) && !names.isEmpty() ? adType.name.in(names) : null;
    }
}
