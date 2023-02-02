package com.adplatform.restApi.advertiser.adgroup.dao.device;

import com.adplatform.restApi.advertiser.adgroup.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
public interface DeviceRepository extends JpaRepository<Device, Integer>, DeviceQuerydslRepository {
    Optional<Device> findByName(String name);
}