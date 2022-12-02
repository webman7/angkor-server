package com.adplatform.restApi.domain.adgroup.dao.adgroup;

import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface AdGroupQuerydslRepository {
    Page<AdGroupDto.Response.AdvertiserSearch> search(AdvertiserSearchRequest request, Pageable pageable);

    Page<AdGroupDto.Response.ForSaveCreative> searchForSaveCreative(AdGroupDto.Request.Search request, Pageable pageable);

    Optional<AdGroup> findByIdFetchJoin(Integer id);
}
