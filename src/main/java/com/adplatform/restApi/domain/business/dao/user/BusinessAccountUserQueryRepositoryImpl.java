package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccountUser.businessAccountUser;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Repository
public class BusinessAccountUserQueryRepositoryImpl implements BusinessAccountUserQueryRepository {
    private final JPAQueryFactory query;

    @Override
    public Optional<BusinessAccountUser> findByBusinessAccountIdAndUserId(Integer businessAccountId, Integer userId) {
        return Optional.ofNullable(this.query.selectFrom(businessAccountUser)
                .where(businessAccountUser.id.businessAccountId.eq(businessAccountId), businessAccountUser.id.userId.eq(userId))
                .fetchOne());
    }
}
