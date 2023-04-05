package com.adplatform.restApi.domain.statistics.domain.taxbill;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_tax_bill_payment_files")
public class MediaTaxBillPaymentFile extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_tax_bill_id")
    private MediaTaxBill mediaTaxBill;

    @Embedded
    private FileInformation information;

    public MediaTaxBillPaymentFile(MediaTaxBill mediaTaxBill, FileInformation information) {
        this.mediaTaxBill = mediaTaxBill;
        this.information = information;
    }

    public MediaTaxBillPaymentFile copy(MediaTaxBill mediaTaxBill) {
        MediaTaxBillPaymentFile copy = new MediaTaxBillPaymentFile();
        copy.mediaTaxBill = mediaTaxBill;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}
