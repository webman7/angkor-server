package com.adplatform.restApi.domain.advertiser.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.advertiser.adgroup.dto.adgroup.AdGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface AdGroupQuerydslRepository {
    Page<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(AdGroupDto.Request.Search request, Pageable pageable);

    Optional<AdGroup> findByIdFetchJoin(Integer id);

    List<AdGroupDto.Response.Budget> getBudget(Integer campaignId);

    Long getBudgetSum(Integer campaignId);
}
