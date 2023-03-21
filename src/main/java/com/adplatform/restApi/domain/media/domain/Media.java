package com.adplatform.restApi.domain.media.domain;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "media_category_info",
            joinColumns = @JoinColumn(name = "media_info_id"),
            inverseJoinColumns = @JoinColumn(name = "category_info_id"))
    private final Set<Category> category = new HashSet<>();

    @Column(name = "url", length = 256)
    private String url;

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MediaFile> mediaFiles = new ArrayList<>();

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
            List<Category> category,
            String url,
            Integer expInventory,
            Status status,
            String memo) {
        this.name = name;
        this.company = company;
        this.category.addAll(category);
        this.url = url;
        this.expInventory = expInventory;
        this.status = status;
        this.memo = memo;
    }

     public Media update(MediaDto.Request.Update request) {
        this.name = request.getName();
        this.url = request.getUrl();
        this.expInventory = request.getExpInventory();
        this.memo = request.getMemo();
        return this;
    }

    public void delete() {
        this.status = Media.Status.D;
    }

    public void addMediaFile(MediaFile file) {
        this.mediaFiles.add(file);
    }
}
