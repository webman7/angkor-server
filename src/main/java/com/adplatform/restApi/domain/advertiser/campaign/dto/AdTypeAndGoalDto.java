package com.adplatform.restApi.domain.advertiser.campaign.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class AdTypeAndGoalDto {
    @NotBlank
    private String adTypeName;
    @NotBlank
    private String adGoalName;

    @QueryProjection
    public AdTypeAndGoalDto(String adTypeName, String adGoalName) {
        this.adTypeName = adTypeName;
        this.adGoalName = adGoalName;
    }
}
