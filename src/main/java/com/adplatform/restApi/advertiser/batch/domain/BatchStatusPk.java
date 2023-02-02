package com.adplatform.restApi.advertiser.batch.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BatchStatusPk implements Serializable {
    public enum Type {
        /** 배치구분:D(일별), M(월별) */
        D, M
    }
    @Column(name = "type", nullable = false, columnDefinition = "CHAR(1)")
    private String type;

    @Column(name = "exe_date", nullable = false)
    private Integer exeDate;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
