package com.adplatform.restApi.domain.media.dao.category.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MediaCategorySaveQueryMapper {
    void insertMediaCategory(Integer mediaId, Integer categoryId);

    void deleteMediaCategory(Integer mediaId);
}
