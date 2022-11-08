package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CreativeQuerydslRepository {
    Page<CreativeDto.Response.Default> search(Pageable pageable, String name);
}
