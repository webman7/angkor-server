package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdGroupQuerydslRepository {
    Map<Integer, Integer> findScheduleFirstStartDateByCampaignId(List<Integer> campaignIds);

    Map<Integer, Integer> findScheduleLastEndDateByCampaignId(List<Integer> campaignIds);

    Page<AdGroupDto.Response.Default> search(AdGroupDto.Request.Search request, Pageable pageable);

    Page<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(AdGroupDto.Request.Search request, Pageable pageable);

    Optional<AdGroup> findByIdFetchJoin(Integer id);
}
