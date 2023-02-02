package com.adplatform.restApi.batch.domain;

import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "batch_status")
public class BatchStatus {

    @EmbeddedId
    private final BatchStatusPk id = new BatchStatusPk();

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "exe_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean exeYn;

    @Builder
    public BatchStatus(
            String type,
            Integer exeDate,
            String name,
            boolean exeYn) {
        this.id.setType(type);
        this.id.setExeDate(exeDate);
        this.id.setName(name);
        this.exeYn = exeYn;
    }
}
