package com.adplatform.restApi.domain.adgroup.dao.device;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.adplatform.restApi.domain.adgroup.domain.QAdGroupDevice.adGroupDevice;

@RequiredArgsConstructor
@Repository
public class DeviceQuerydslRepositoryImpl implements DeviceQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public void deleteByAdGroupId(Integer adGroupId) {
        this.query.delete(adGroupDevice).where(adGroupDevice.id.adGroupId.eq(adGroupId)).execute();
    }
}
