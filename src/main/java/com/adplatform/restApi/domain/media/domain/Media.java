package com.adplatform.restApi.domain.media.domain;

import com.adplatform.restApi.domain.business.domain.BusinessAccountUser;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "media_info")
public class Media extends BaseUpdatedEntity {

    public enum Status {
        /** 승인 */
        Y,
        /** 요청 */
        N,
        /** 거부 */
        R,
        /** 삭제 */
        D
    }

    @Column(name = "name", length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_info_id")
    private Company company;

    @Column(name = "app_key", length = 30)
    private String appKey;

    @Column(name = "app_secret", length = 128)
    private String appSecret;

    @Column(name = "url", length = 256)
    private String url;

    @Column(name = "exp_inventory", columnDefinition = "INT")
    private Integer expInventory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "CHAR")
    private Status status;

    @Column(name = "memo", length = 2000)
    private String memo;

    @Column(name = "admin_memo", length = 1000)
    private String adminMemo;

    @Column(name = "approve_user_no")
    private Integer approveUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "approve_date")
    private LocalDateTime approveAt;

    @Builder
    public Media(
            String name,
            Company company,
            String url,
            Integer expInventory,
            Status status,
            String memo) {
        this.name = name;
        this.company = company;
        this.url = url;
        this.expInventory = expInventory;
        this.status = status;
        this.memo = memo;
    }
}
