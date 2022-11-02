package com.adplatform.restApi.domain.campaign.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AdTypeAndGoalDto {
    @NotBlank
    private String adTypeName;
    @NotBlank
    private String adGoalName;
}
