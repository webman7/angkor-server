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
@Table(name = "media_tax_bill_files")
public class MediaTaxBillFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_tax_bill_id")
    private MediaTaxBill mediaTaxBill;

    @Embedded
    private FileInformation information;

    public MediaTaxBillFile(MediaTaxBill mediaTaxBill, FileInformation information) {
        this.mediaTaxBill = mediaTaxBill;
        this.information = information;
    }

    public MediaTaxBillFile copy(MediaTaxBill mediaTaxBill) {
        MediaTaxBillFile copy = new MediaTaxBillFile();
        copy.mediaTaxBill = mediaTaxBill;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}