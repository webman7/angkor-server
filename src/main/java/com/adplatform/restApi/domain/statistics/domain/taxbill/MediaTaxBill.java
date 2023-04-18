package com.adplatform.restApi.domain.statistics.domain.taxbill;

import com.adplatform.restApi.domain.bank.domain.Bank;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.domain.MediaFile;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.statistics.dto.TaxBillDto;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_tax_bill")
public class MediaTaxBill extends BaseCreatedEntity {
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

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @OneToMany(mappedBy = "mediaTaxBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MediaTaxBillFile> mediaTaxBillFiles = new ArrayList<>();

    @OneToMany(mappedBy = "mediaTaxBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MediaTaxBillPaymentFile> mediaTaxBillPaymentFiles = new ArrayList<>();

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "issue_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean issueStatus;

    @Column(name = "issue_user_no")
    private Integer issueUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "issue_date")
    private LocalDateTime issueAt;

    @ManyToOne
    @JoinColumn(name = "bank_info_id")
    private Bank bank;

    @Column(name = "account_number", length = 30)
    private String accountNumber;

    @Column(name = "account_owner", length = 50)
    private String accountOwner;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "payment_status", nullable = false, columnDefinition = "CHAR(1)")
    private boolean paymentStatus;

    @Column(name = "payment_user_no")
    private Integer paymentUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "payment_date")
    private LocalDateTime paymentAt;

    @Builder
    public MediaTaxBill(
            Integer mediaId,
            Integer companyId,
            Integer statDate,
            Float supplyAmount,
            Float vatAmount,
            Float totalAmount,
            String memo,
            boolean issueStatus) {
        this.mediaId = mediaId;
        this.companyId = companyId;
        this.statDate = statDate;
        this.supplyAmount = supplyAmount;
        this.vatAmount = vatAmount;
        this.totalAmount = totalAmount;
        this.memo = memo;
        this.issueStatus = issueStatus;
    }

    public MediaTaxBill update(TaxBillDto.Request.Update request) {
        this.statDate = request.getStatDate();
        this.supplyAmount = request.getSupplyAmount();
        this.vatAmount = request.getVatAmount();
        this.totalAmount = request.getTotalAmount();
        this.memo = request.getMemo();
        return this;
    }

    public MediaTaxBill updatePayment(TaxBillDto.Request.Payment request, Bank bank) {
        String startYear = request.getPaymentDate().toString().substring(0, 4);
        String startMonth = request.getPaymentDate().toString().substring(4, 6);
        String startDay = request.getPaymentDate().toString().substring(6, 8);
        String  paymentDate = startYear + "-" + startMonth + "-" + startDay + " 00:00:00";
        this.bank = bank;
        this.accountNumber = request.getAccountNumber();
        this.accountOwner = request.getAccountOwner();
        this.paymentUserNo = SecurityUtils.getLoginUserNo();
        this.paymentAt = LocalDateTime.parse(paymentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.paymentStatus = true;
        this.adminMemo = request.getAdminMemo();
        return this;
    }

    public void addMediaTaxBillFile(MediaTaxBillFile file) {
        this.mediaTaxBillFiles.add(file);
    }

    public void addMediaTaxBillPaymentFile(MediaTaxBillPaymentFile file) {
        this.mediaTaxBillPaymentFiles.add(file);
    }

    public MediaTaxBill updateIssue() {
        this.issueUserNo = SecurityUtils.getLoginUserNo();
        this.issueAt = LocalDateTime.now();
        this.issueStatus = true;
        return this;
    }
}
