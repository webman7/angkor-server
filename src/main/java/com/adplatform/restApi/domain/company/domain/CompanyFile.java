package com.adplatform.restApi.domain.company.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company_files")
public class CompanyFile extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_info_id")
    private Company company;

    @Column(name = "type", length = 20)
    private String type;

    @Embedded
    private FileInformation information;

    public CompanyFile(Company company, String type, FileInformation information) {
        this.company = company;
        this.type = type;
        this.information = information;
    }

    public CompanyFile copy(Company company) {
        CompanyFile copy = new CompanyFile();
        copy.company = company;
        copy.type = this.type;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}