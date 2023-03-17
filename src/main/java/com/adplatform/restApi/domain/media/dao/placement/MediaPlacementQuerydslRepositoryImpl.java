package com.adplatform.restApi.domain.media.dao.placement;

import com.adplatform.restApi.domain.media.dao.category.MediaCategoryQuerydslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MediaPlacementQuerydslRepositoryImpl implements MediaCategoryQuerydslRepository {

    private final JPAQueryFactory query;
}
