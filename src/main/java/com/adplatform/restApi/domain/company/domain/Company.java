package com.adplatform.restApi.domain.company.domain;

import com.adplatform.restApi.domain.adaccount.domain.AdAccount;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import com.adplatform.restApi.global.value.Address;
import com.adplatform.restApi.global.value.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company_info")
public class Company extends BaseUpdatedEntity {
    /**
     * 회사 타입
     */
    public enum Type {
        /** 비즈니스 */
        BUSINESS,
        /** 매체사 */
        MEDIA
    }

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type")
    private Type type;

    @Column(name = "registration_number", length = 20)
    private String registrationNumber;

    @Column(name = "representation_name", length = 50)
    private String representationName;

    @Embedded
    private Address address;

    @Column(name = "business_category", length = 20)
    private String businessCategory;

    @Column(name = "business_item", length = 50)
    private String businessItem;

    @Valid
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "tax_bill_email", length = 60))
    private Email taxBillEmail;

    /**
     * 삭제 여부.<br/>
     * {@link Boolean#TRUE true}: 삭제됨.<br/>
     * {@link Boolean#FALSE false}: 사용중.
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean deleted;

    @Builder
    public Company(
            String name,
            Type type,
            String registrationNumber,
            String representationName,
            Address address,
            String businessCategory,
            String businessItem,
            Email taxBillEmail) {
        this.name = name;
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.representationName = representationName;
        this.address = address;
        this.businessCategory = businessCategory;
        this.businessItem = businessItem;
        this.taxBillEmail = new Email(taxBillEmail.getAddress());
        this.deleted = false;
    }

    public Company update(CompanyDto.Request.Update request) {
        this.name = request.getName();
        this.registrationNumber = request.getRegistrationNumber();
        this.representationName = request.getRepresentationName();
        this.address = request.getAddress();
        this.businessCategory = request.getBusinessCategory();
        this.businessItem = request.getBusinessItem();
        this.taxBillEmail = new Email(request.getTaxBillEmail());
        return this;
    }

    public void delete() {
        this.deleted = true;
    }
}
