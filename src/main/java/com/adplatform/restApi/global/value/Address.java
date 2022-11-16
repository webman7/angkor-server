package com.adplatform.restApi.global.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Address {
    @Column(name = "base_address", length = 100)
    private String baseAddress;

    @Column(name = "detail_address", length = 50)
    private String detailAddress;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    public Address(String baseAddress, String detailAddress, String zipCode) {
        this.baseAddress = baseAddress;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
    }
}
