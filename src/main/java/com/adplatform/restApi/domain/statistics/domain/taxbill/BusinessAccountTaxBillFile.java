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
@Table(name = "business_account_tax_bill_files")
public class BusinessAccountTaxBillFile extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_account_tax_bill_id")
    private BusinessAccountTaxBill businessAccountTaxBill;

    @Embedded
    private FileInformation information;

    public BusinessAccountTaxBillFile(BusinessAccountTaxBill businessAccountTaxBill, FileInformation information) {
        this.businessAccountTaxBill = businessAccountTaxBill;
        this.information = information;
    }

    public BusinessAccountTaxBillFile copy(BusinessAccountTaxBill businessAccountTaxBill) {
        BusinessAccountTaxBillFile copy = new BusinessAccountTaxBillFile();
        copy.businessAccountTaxBill = businessAccountTaxBill;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}
