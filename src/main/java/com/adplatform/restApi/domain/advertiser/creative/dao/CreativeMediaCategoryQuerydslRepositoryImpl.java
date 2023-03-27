package com.adplatform.restApi.domain.advertiser.creative.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreativeMediaCategory.creativeMediaCategory;

@RequiredArgsConstructor
@Repository
public class CreativeMediaCategoryQuerydslRepositoryImpl implements CreativeMediaCategoryQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public void deleteCreativeMediaCategory(Integer creativeId) {
        this.query.delete(creativeMediaCategory).where(creativeMediaCategory.id.creativeId.eq(creativeId)).execute();
    }
}
