package com.adplatform.restApi.domain.campaign.dao;

import com.adplatform.restApi.domain.campaign.domain.Campaign;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;
import static java.util.Objects.nonNull;

public class CampaignCondition {
    public static BooleanExpression eqId(Integer id) {
        return nonNull(id) ? campaign.id.eq(id) : null;
    }

    public static BooleanExpression inId(List<Integer> ids) {
        return nonNull(ids) && !ids.isEmpty() ? campaign.id.in(ids) : null;
    }

    public static BooleanExpression containsName(String name) {
        return StringUtils.isNotBlank(name) ? campaign.name.contains(name) : null;
    }

    public static BooleanExpression inConfig(List<Campaign.Config> configs) {
        return nonNull(configs) && !configs.isEmpty() ? campaign.config.in(configs) : null;
    }

    public static BooleanExpression inConfigElseWithOutStatusDel(List<Campaign.Config> configs) {
        return nonNull(configs) && !configs.isEmpty() ? campaign.config.in(configs) : campaign.config.ne(Campaign.Config.DEL);
    }

    public static BooleanExpression inStatus(List<Campaign.Status> statuses) {
        return nonNull(statuses) && !statuses.isEmpty() ? campaign.status.in(statuses) : null;
    }

    public static BooleanExpression inStatusElseWithOutStatusDel(List<Campaign.Status> statuses) {
        return nonNull(statuses) && !statuses.isEmpty() ? campaign.status.in(statuses) : campaign.status.ne(Campaign.Status.DELETED);
    }
}
