package com.adplatform.restApi.advertiser.campaign.dao.typegoal;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.advertiser.campaign.domain.QAdGoal.adGoal;

public class AdGoalCondition {
    public static BooleanExpression eqName(String name) {
        return StringUtils.isNotBlank(name) ? adGoal.name.eq(name) : null;
    }

    public static BooleanExpression inName(List<String> names) {
        return Objects.nonNull(names) && !names.isEmpty() ? adGoal.name.in(names) : null;
    }
}
