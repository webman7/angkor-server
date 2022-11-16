package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.dto.CreativeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface CreativeQuerydslRepository {
    Page<CreativeDto.Response.Default> search(CreativeDto.Request.Search request, Pageable pageable);
}
