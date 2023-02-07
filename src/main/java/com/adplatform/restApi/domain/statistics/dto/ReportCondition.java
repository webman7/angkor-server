package com.adplatform.restApi.domain.statistics.dto;

import com.adplatform.restApi.global.constant.Range;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
public class ReportCondition {
    @NotNull
    private ReportIndicator indicator;
    @NotNull
    private Range conditionFunction;
    @NotNull
    private Integer value;
}
