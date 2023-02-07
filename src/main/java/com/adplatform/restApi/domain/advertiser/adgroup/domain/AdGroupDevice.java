package com.adplatform.restApi.domain.advertiser.adgroup.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "adgroup_device_info")
public class AdGroupDevice {
    @EmbeddedId
    private final AdGroupDeviceId id = new AdGroupDeviceId();

    @MapsId("adGroupId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adgroup_info_id")
    private AdGroup adGroup;

    @MapsId("deviceId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_info_id")
    private Device device;
}
