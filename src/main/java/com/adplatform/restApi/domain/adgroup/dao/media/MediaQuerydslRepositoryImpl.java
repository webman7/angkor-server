package com.adplatform.restApi.domain.adgroup.dao.media;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupMedia.adGroupMedia;

@RequiredArgsConstructor
@Repository
public class MediaQuerydslRepositoryImpl implements MediaQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public void deleteByAdGroupId(Integer adGroupId) {
        this.query.delete(adGroupMedia).where(adGroupMedia.id.adGroupId.eq(adGroupId)).execute();
    }
}
