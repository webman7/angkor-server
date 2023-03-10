package com.adplatform.restApi.domain.business.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.business.dto.user.BusinessAccountUserDto;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccountUser.businessAccountUser;

/**
 * @author junny
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

    @Override
    public Integer findByBusinessAccountIdAndUserIdCount(Integer businessAccountId, Integer userId) {
        List<Integer> content = this.query.select(
                        businessAccountUser.id.businessAccountId
                )
                .from(businessAccountUser)
                .where(businessAccountUser.id.businessAccountId.eq(businessAccountId), businessAccountUser.id.userId.eq(userId))
                .fetch();

        return content.size();
    }
}
