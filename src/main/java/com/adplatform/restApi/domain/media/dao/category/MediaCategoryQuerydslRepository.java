package com.adplatform.restApi.domain.media.dao.category;

public interface MediaCategoryQuerydslRepository {

    void deleteMediaCategory(Integer mediaId);

    void insertMediaCategory(Integer mediaId, Integer categoryId);
}
