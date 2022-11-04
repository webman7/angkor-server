package com.adplatform.restApi.domain.creative.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "creative_option_proof")
public class CreativeOptionProof extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "creative_info_id")
    private Creative creative;

    @Column(name = "url", length = 1000)
    private String url;

    @Column(name = "file_type", length = 20)
    private String fileType;

    @Column(name = "file_size")
    private int fileSize;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "original_file_name", length = 200)
    private String originalFileName;

    @Column(name = "width")
    private int width;

    @Column(name = "height")
    private int height;

    @Column(name = "mime_type", length = 100)
    private String mimeType;
}
