package com.adplatform.restApi.domain.media.dao.category.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MediaCategorySaveQueryMapper {
    void insertMediaCategory(@Param("mediaId") Integer mediaId, @Param("categoryId") Integer categoryId);

    void deleteMediaCategory(@Param("mediaId") Integer mediaId);
}
