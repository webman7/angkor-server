package com.adplatform.restApi.domain.creative.dao;

import com.adplatform.restApi.domain.creative.domain.Creative;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.adplatform.restApi.domain.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.creative.domain.QCreative.creative;
import static com.adplatform.restApi.domain.creative.domain.QCreativeFile.creativeFile;
import static com.adplatform.restApi.domain.creative.domain.QCreativeOpinionProofFile.creativeOpinionProofFile;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class CreativeQuerydslRepositoryImpl implements CreativeQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<Creative> findDetailById(Integer id) {
        return Optional.ofNullable(this.query.selectFrom(creative)
                .leftJoin(creative.files, creativeFile).fetchJoin()
                .leftJoin(creative.opinionProofFiles, creativeOpinionProofFile)
                .where(creative.id.eq(id))
                .fetchOne());
    }

    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return Objects.nonNull(adAccountId) ? campaign.adAccount.id.eq(adAccountId) : null;
    }
}
