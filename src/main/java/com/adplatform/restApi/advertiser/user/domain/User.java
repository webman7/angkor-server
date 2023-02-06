package com.adplatform.restApi.advertiser.user.domain;

import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import com.adplatform.restApi.global.value.Email;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_no", columnDefinition = "INT"))
@Entity
@Table(name = "user_info")
@ToString
public class User extends BaseUpdatedEntity {
    public enum Active {
        /**
         * Y: 사용중
         * N: 삭제
         * W: 승인대기
         * L: 잠금
         */
        Y, N, W, L
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_info_id")
    private Company company;

    @Column(name = "user_id", nullable = false, length = 256, unique = true)
    private String loginId;

    @Embedded
    @Column(name = "user_password", nullable = false, length = 128)
    private Password password;

    @Column(name = "user_name", nullable = false, length = 50)
    private String name;

    @Valid
    @Embedded
    private Email email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "active", nullable = false, columnDefinition = "CHAR(1)")
    private Active active;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<UserRole> roles = new HashSet<>();

    @Column(name = "status_chg_user_no")
    private Integer statusChangedUserNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "status_chg_date")
    private LocalDateTime statusChangedAt;

    @Builder
    public User(Company company, String loginId, String password, String name, String email, String phone, Active active) {
        this.company = company;
        this.loginId = loginId;
        this.password = new Password(password);
        this.name = name;
        this.email = new Email(email);
        this.phone = phone;
        this.active = active;
    }

    public User updateCompany(Company company) {
        this.company = company;
        return this;
    }

    public User updateRole(UserRole role) {
        this.roles.add(role);
        return this;
    }

    public User updateActive(Active active) {
        this.active = active;
        return this;
    }
}
