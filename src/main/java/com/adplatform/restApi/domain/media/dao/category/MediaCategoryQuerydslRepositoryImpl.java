package com.adplatform.restApi.domain.media.dao.category;

import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.MediaCategory;
import com.adplatform.restApi.domain.media.dto.category.MediaCategoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreative_CreativePlacement.creativePlacement;
import static com.adplatform.restApi.domain.media.domain.QCategory.category;
import static com.adplatform.restApi.domain.media.domain.QMediaCategory.mediaCategory;

@RequiredArgsConstructor
@Repository
public class MediaCategoryQuerydslRepositoryImpl implements MediaCategoryQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Category> findByMediaIdCategory(Integer id) {
        return this.query.select(mediaCategory.category)
                .from(mediaCategory, category)
                .where(mediaCategory.media.id.eq(id),
                       category.deleted.eq(false),
                       mediaCategory.category.id.eq(category.id))
                .fetch();
    }

}
