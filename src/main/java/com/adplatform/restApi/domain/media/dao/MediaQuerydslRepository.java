package com.adplatform.restApi.domain.media.dao;

import com.adplatform.restApi.domain.media.dto.MediaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MediaQuerydslRepository {

    Page<MediaDto.Response.Search> search(Pageable pageable, MediaDto.Request.Search searchRequest);







    // 이전 작업
    void deleteByAdGroupId(Integer adGroupId);
}
