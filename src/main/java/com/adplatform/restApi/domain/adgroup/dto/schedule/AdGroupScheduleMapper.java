package com.adplatform.restApi.domain.adgroup.dto.schedule;

import com.adplatform.restApi.domain.adgroup.domain.AdGroupSchedule;
import com.adplatform.restApi.domain.adgroup.domain.ScheduleTime;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface AdGroupScheduleMapper {
    @Mapping(target = "adGroup", ignore = true)
    AdGroupSchedule toEntity(AdGroupScheduleDto.Request.FirstSave dto);

    default ScheduleTime toAdGroupSchedule(List<Boolean> time) {
        return new ScheduleTime(time);
    }
}
