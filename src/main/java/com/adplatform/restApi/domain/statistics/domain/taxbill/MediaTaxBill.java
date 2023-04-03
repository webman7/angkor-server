package com.adplatform.restApi.domain.statistics.domain.taxbill;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_tax_bill")
public class MediaTaxBill extends BaseEntity {
    @Column(name = "business_account_info_id")
    private int businessAccountId;

    @Column(name = "adaccount_info_id")
    private int adAccountId;

    @Column(name = "media_info_id")
    private int mediaId;

    @Column(name = "company_info_id")
    private int companyId;

    @Column(name = "stat_date")
    private int statDate;

    @Column(name = "supply_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float supplyAmount;

    @Column(name = "vat_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float vatAmount;

    @Column(name = "total_amount", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2)")
    private Float totalAmount;

    @Column(name = "memo", length = 2000)
    private String memo;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "issue_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean issueStatus;

    @Column(name = "issue_user_no")
    private int issueUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "issue_date")
    private LocalDateTime issueAt;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "payment_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean paymentStatus;

    @Column(name = "payment_user_no")
    private int paymentUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "payment_date")
    private LocalDateTime paymentAt;

    @Builder
    public MediaTaxBill(
            Integer businessAccountId,
            Integer adAccountId,
            Integer mediaId,
            Integer companyId,
            Integer statDate,
            Float supplyAmount,
            Float vatAmount,
            Float totalAmount,
            boolean issueStatus) {
        this.businessAccountId = businessAccountId;
        this.adAccountId = adAccountId;
        this.mediaId = mediaId;
        this.companyId = companyId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
        this.issueStatus = issueStatus;
    }
}
