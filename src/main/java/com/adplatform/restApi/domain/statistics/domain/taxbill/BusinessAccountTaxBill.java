package com.adplatform.restApi.domain.statistics.domain.taxbill;

import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "business_account_tax_bill")
public class BusinessAccountTaxBill extends BaseEntity {
    @Column(name = "business_account_info_id")
    private int businessAccountId;

    @Column(name = "stat_date")
    private int statDate;

    @Column(name = "supply_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float supplyAmount;

    @Column(name = "vat_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float vatAmount;

    @Column(name = "total_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float totalAmount;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @OneToMany(mappedBy = "businessAccountTaxBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<BusinessAccountTaxBillFile> businessAccountTaxBillFiles = new ArrayList<>();

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "issue_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean issueStatus;

    @Column(name = "issue_user_no")
    private Integer issueUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "issue_date")
    private LocalDateTime issueAt;

    @Builder
    public BusinessAccountTaxBill(
            Integer businessAccountId,
            Integer statDate,
            Float supplyAmount,
            Float vatAmount,
            Float totalAmount,
            boolean issueStatus) {
        this.businessAccountId = businessAccountId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
        this.issueStatus = issueStatus;
    }

    public BusinessAccountTaxBill update(TaxBillDto.Request.BusinessTaxBillUpdate request) {
        this.adminMemo = request.getAdminMemo();
        this.issueUserNo = SecurityUtils.getLoginUserNo();
        this.issueAt = LocalDateTime.now();
        this.issueStatus = true;
        return this;
    }

    public void addBusinessAccountTaxBillFile(BusinessAccountTaxBillFile file) {
        this.businessAccountTaxBillFiles.add(file);
    }
}