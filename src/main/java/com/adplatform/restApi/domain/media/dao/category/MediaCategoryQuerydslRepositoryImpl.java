package com.adplatform.restApi.domain.media.dao.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MediaCategoryQuerydslRepositoryImpl implements MediaCategoryQuerydslRepository {

    private final JPAQueryFactory query;
}
