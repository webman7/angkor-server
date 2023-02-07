package com.adplatform.restApi.domain.agency.businessright.dao;

import com.adplatform.restApi.domain.agency.businessright.dto.BusinessRightDto;
import com.adplatform.restApi.domain.agency.businessright.dto.QBusinessRightDto_Response_BusinessRightDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.agency.businessright.domain.QBusinessRight.businessRight;

@RequiredArgsConstructor
@Repository
public class BusinessRightQuerydslRepositoryImpl implements BusinessRightQuerydslRepository {

    private final JPAQueryFactory query;
    @Override
    public List<BusinessRightDto.Response.BusinessRightDetail> getBusinessRight(Integer adAccount, Integer companyId) {
        return this.query.select(new QBusinessRightDto_Response_BusinessRightDetail(
                        businessRight.id,
                        businessRight.adAccountId,
                        businessRight.companyId,
                        businessRight.startDate,
                        businessRight.endDate
                ))
                .from(businessRight)
                .where(businessRight.adAccountId.eq(adAccount),
                        businessRight.companyId.eq(companyId),
                        businessRight.endDate.eq(29991231)
                )
                .fetch();
    }
}
