package com.adplatform.restApi.domain.adgroup.dao;

import com.adplatform.restApi.domain.adgroup.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Optional<Device> findByName(String name);
}
