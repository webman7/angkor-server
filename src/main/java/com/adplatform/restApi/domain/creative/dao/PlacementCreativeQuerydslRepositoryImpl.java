package com.adplatform.restApi.domain.creative.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.creative.domain.QCreative_PlacementCreative.placementCreative;

@RequiredArgsConstructor
@Repository
public class PlacementCreativeQuerydslRepositoryImpl implements PlacementCreativeQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public void deletePlacementCreative(Integer creativeId) {
        this.query.delete(placementCreative).where(placementCreative.id.creativeId.eq(creativeId)).execute();
    }
}
