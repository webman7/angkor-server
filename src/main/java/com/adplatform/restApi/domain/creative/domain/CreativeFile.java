package com.adplatform.restApi.domain.creative.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "creative_files")
public class CreativeFile extends BaseEntity {
    public enum Type {
        PROFILE, PROMOTIONAL, CATALOG
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creative_info_id")
    private Creative creative;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
    private Type type;

    @Embedded
    private FileInformation information;

    public CreativeFile(Creative creative, Type type, FileInformation information) {
        this.creative = creative;
        this.type = type;
        this.information = information;
    }
}
