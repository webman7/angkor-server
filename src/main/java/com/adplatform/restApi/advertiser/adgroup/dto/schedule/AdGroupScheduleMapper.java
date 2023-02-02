package com.adplatform.restApi.advertiser.adgroup.dto.schedule;

import com.adplatform.restApi.advertiser.adgroup.domain.AdGroupSchedule;
import com.adplatform.restApi.advertiser.adgroup.domain.ScheduleTime;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Mapper(config = BaseMapperConfig.class)
public interface AdGroupScheduleMapper {
    @Mapping(target = "adGroup", ignore = true)
    AdGroupSchedule toEntity(AdGroupScheduleDto.Request.FirstSave dto);

    @Mapping(target = "mondayTime", expression = "java(adGroupSchedule.getMondayTime().getTime())")
    @Mapping(target = "tuesdayTime", expression = "java(adGroupSchedule.getTuesdayTime().getTime())")
    @Mapping(target = "wednesdayTime", expression = "java(adGroupSchedule.getWednesdayTime().getTime())")
    @Mapping(target = "thursdayTime", expression = "java(adGroupSchedule.getThursdayTime().getTime())")
    @Mapping(target = "fridayTime", expression = "java(adGroupSchedule.getFridayTime().getTime())")
    @Mapping(target = "saturdayTime", expression = "java(adGroupSchedule.getSaturdayTime().getTime())")
    @Mapping(target = "sundayTime", expression = "java(adGroupSchedule.getSundayTime().getTime())")
    @Mapping(target = "hasDetailTime", expression = "java(adGroupSchedule.hasDetailTime())")
    AdGroupScheduleDto.Response.Default toDefaultResponse(AdGroupSchedule adGroupSchedule);

    default ScheduleTime toAdGroupSchedule(List<Boolean> time) {
        return new ScheduleTime(time);
    }
}
