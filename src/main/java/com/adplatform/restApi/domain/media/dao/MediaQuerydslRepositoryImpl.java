package com.adplatform.restApi.domain.media.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MediaQuerydslRepositoryImpl implements MediaQuerydslRepository {

    private final JPAQueryFactory query;
}
