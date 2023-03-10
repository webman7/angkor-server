package com.adplatform.restApi.domain.advertiser.creative.dao;

import com.adplatform.restApi.domain.advertiser.creative.domain.Creative;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeFile;
import com.adplatform.restApi.domain.advertiser.creative.domain.CreativeOpinionProofFile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.adplatform.restApi.domain.advertiser.campaign.domain.QCampaign.campaign;
import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreative.creative;
import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreativeFile.creativeFile;
import static com.adplatform.restApi.domain.advertiser.creative.domain.QCreativeOpinionProofFile.creativeOpinionProofFile;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class CreativeQuerydslRepositoryImpl implements CreativeQuerydslRepository {
    private final JPAQueryFactory query;

//    @Override
//    public Optional<Creative> findDetailById(Integer id) {
//        return Optional.ofNullable(this.query.selectFrom(creative)
//                .leftJoin(creative.files, creativeFile).fetchJoin()
//                .leftJoin(creative.opinionProofFiles, creativeOpinionProofFile)
//                .where(creative.id.eq(id))
//                .fetchOne());
//    }

    @Override
    public Optional<Creative> findDetailById(Integer id) {
        return Optional.ofNullable(this.query.selectFrom(creative)
                .where(creative.id.eq(id))
                .fetchOne());
    }

    @Override
    public List<CreativeFile> findDetailFilesById(Integer id) {
        return this.query.select(creativeFile)
                .from(creative, creativeFile)
                .where(creative.id.eq(id),
                        creative.id.eq(creativeFile.creative.id))
                .fetch();
    }

    @Override
    public List<CreativeOpinionProofFile> findDetailOpinionProofById(Integer id) {
        return this.query.select(creativeOpinionProofFile)
                .from(creative, creativeOpinionProofFile)
                .where(creative.id.eq(id),
                        creative.id.eq(creativeOpinionProofFile.creative.id))
                .fetch();
    }

    private BooleanExpression eqAdAccountId(Integer adAccountId) {
        return Objects.nonNull(adAccountId) ? campaign.adAccount.id.eq(adAccountId) : null;
    }
}
