package com.adplatform.restApi.domain.adgroup.dto.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.statistics.dto.ReportCondition;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
public class AdvertiserSearchAdGroupCondition {
    private List<Integer> ids;
    private String name;
    private List<AdGroup.Config> configs;
    private List<AdGroup.Status> statuses;
    @Valid
    private List<ReportCondition> reportConditions;
}
