package com.adplatform.restApi.domain.creative.dto;

import com.adplatform.restApi.domain.creative.domain.Creative;
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
public class AdvertiserSearchCreativeCondition {
    private List<Integer> ids;
    private String name;
    private List<Creative.Format> formats;
    private List<Creative.Config> configs;
    private List<Creative.Status> statuses;
    private List<Creative.ReviewStatus> reviewStatuses;
    @Valid
    private List<ReportCondition> reportConditions;
}
