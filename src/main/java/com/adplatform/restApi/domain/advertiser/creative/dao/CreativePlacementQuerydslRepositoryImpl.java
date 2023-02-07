package com.adplatform.restApi.domain.advertiser.creative.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreative_CreativePlacement.creativePlacement;

@RequiredArgsConstructor
@Repository
public class CreativePlacementQuerydslRepositoryImpl implements CreativePlacementQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public void deleteCreativePlacement(Integer creativeId) {
        this.query.delete(creativePlacement).where(creativePlacement.id.creativeId.eq(creativeId)).execute();
    }
}
