package com.adplatform.restApi.domain.media.dao.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreative_CreativePlacement.creativePlacement;
import static com.adplatform.restApi.domain.media.domain.QMediaCategory.mediaCategory;

@RequiredArgsConstructor
@Repository
public class MediaCategoryQuerydslRepositoryImpl implements MediaCategoryQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public void deleteMediaCategory(Integer mediaId) {
        this.query.delete(mediaCategory).where(mediaCategory.id.mediaId.eq(mediaId)).execute();
    }

    @Override
    public void insertMediaCategory(Integer mediaId, Integer categoryId) {
//        this.query.insert(mediaCategory).columns(mediaCategory.media.id, mediaCategory.category.id)
//                .values(mediaId, categoryId).execute();
    }
}
