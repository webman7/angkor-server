package com.adplatform.restApi.domain.adaccount.dao.user;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccountUser.adAccountUser;

@RequiredArgsConstructor
@Repository
public class AdAccountUserQueryRepositoryImpl implements AdAccountUserQueryRepository{
    private final JPAQueryFactory query;

    @Override
    public Optional<AdAccountUser> findByAdAccountIdAndUserId(Integer adAccountId, Integer userId) {
        return Optional.ofNullable(this.query.selectFrom(adAccountUser)
                .where(adAccountUser.id.adAccountId.eq(adAccountId), adAccountUser.id.userId.eq(userId))
                .fetchOne());
    }
}
