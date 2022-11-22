package com.adplatform.restApi.domain.campaign.dao;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;
import java.util.Objects;

import static com.adplatform.restApi.domain.campaign.domain.QAdGoal.adGoal;

public class AdGoalCondition {
    public static BooleanExpression inName(List<String> names) {
        return Objects.nonNull(names) && !names.isEmpty() ? adGoal.name.in(names) : null;
    }
}
