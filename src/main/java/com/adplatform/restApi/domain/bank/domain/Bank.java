package com.adplatform.restApi.domain.bank.domain;

import com.adplatform.restApi.domain.bank.dto.BankDto;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "bank_info")
public class Bank extends BaseUpdatedEntity {

    @Column(name = "name")
    private String name;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean deleted;

    @Builder
    public Bank(
            String name) {
        this.name = name;
        this.deleted = false;
    }

    public Bank update(BankDto.Request.Update request) {
        this.name = request.getName();
        return this;
    }

    public void delete() {
        this.deleted = true;
    }
}
