package com.adplatform.restApi.domain.advertiser.adgroup.dao.device;

import com.adplatform.restApi.domain.advertiser.adgroup.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author junny
 * @since 1.0
 */
public interface DeviceRepository extends JpaRepository<Device, Integer>, DeviceQuerydslRepository {
    Optional<Device> findByName(String name);
}
