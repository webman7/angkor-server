package com.adplatform.restApi.domain.adgroup.api;

import com.adplatform.restApi.domain.adgroup.dao.DeviceRepository;
import com.adplatform.restApi.domain.adgroup.dto.device.DeviceDto;
import com.adplatform.restApi.domain.adgroup.dto.device.DeviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/devices")
public class DeviceApi {
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @GetMapping
    public ResponseEntity<List<DeviceDto.Response.Default>> findAll() {
        return ResponseEntity.ok(this.deviceMapper.toDefaultResponse(this.deviceRepository.findAll()));
    }
}
