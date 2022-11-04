package com.adplatform.restApi.domain.adgroup.service;

import com.adplatform.restApi.domain.adgroup.dao.adgroup.AdGroupRepository;
import com.adplatform.restApi.domain.adgroup.domain.AdGroup;
import com.adplatform.restApi.domain.adgroup.exception.AdGroupNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdGroupSearchService {
    private final AdGroupRepository adGroupRepository;

    public AdGroup findById(Integer id) {
        return this.adGroupRepository.findById(id)
                .orElseThrow(AdGroupNotFoundException::new);
    }
}
