package com.adplatform.restApi.domain.company.domain;

import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
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
 * @author junny
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

    @Column(name = "base_address", length = 100)
    private String baseAddress;

    @Column(name = "detail_address", length = 50)
    private String detailAddress;

    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Column(name = "business_category", length = 20)
    private String businessCategory;

    @Column(name = "business_item", length = 50)
    private String businessItem;

    @Valid
    @Embedded
    @AttributeOverride(name = "address", column = @Column(name = "tax_bill_email", length = 60))
    private Email taxBillEmail;

    @ManyToOne
    @JoinColumn(name = "bank_info_id")
    private Bank bank;

    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Column(name = "account_owner", length = 50)
    private String accountOwner;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CompanyFile> businessFiles = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CompanyFile> bankFiles = new ArrayList<>();

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
            String baseAddress,
            String detailAddress,
            String zipCode,
            String businessCategory,
            String businessItem,
            Email taxBillEmail,
            Bank bank,
            String accountNumber,
            String accountOwner) {
        this.name = name;
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.representationName = representationName;
        this.baseAddress = baseAddress;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.businessCategory = businessCategory;
        this.businessItem = businessItem;
        this.taxBillEmail = new Email(taxBillEmail.getAddress());
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.deleted = false;
    }

    public Company update(CompanyDto.Request.Update request, Bank bank) {
        this.name = request.getName();
        this.registrationNumber = request.getRegistrationNumber();
        this.representationName = request.getRepresentationName();
        this.baseAddress = request.getBaseAddress();
        this.detailAddress = request.getDetailAddress();
        this.zipCode = request.getZipCode();
        this.businessCategory = request.getBusinessCategory();
        this.businessItem = request.getBusinessItem();
        this.taxBillEmail = new Email(request.getTaxBillEmail());
        this.bank = bank;
        this.accountNumber = request.getAccountNumber();
        this.accountOwner = request.getAccountOwner();
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public void addBusinessFile(CompanyFile file) {
        this.businessFiles.add(file);
    }

    public void addBankFile(CompanyFile file) {
        this.bankFiles.add(file);
    }
}
