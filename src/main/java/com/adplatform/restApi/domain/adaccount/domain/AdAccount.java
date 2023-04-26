package com.adplatform.restApi.domain.adaccount.domain;

import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import com.adplatform.restApi.domain.advertiser.campaign.domain.Campaign;
import com.adplatform.restApi.domain.business.domain.BusinessAccount;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adaccount_info")
@DynamicInsert
public class AdAccount extends BaseUpdatedEntity {

    public enum Config {
        ON, OFF, DEL
    }

    @OneToMany(mappedBy = "adAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<AdAccountUser> adAccountUsers = new ArrayList<>();

    @OneToMany(mappedBy = "adAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Campaign> campaigns = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "business_account_info_id")
    private BusinessAccount businessAccount;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "config", nullable = false, columnDefinition = "CHAR(5)")
    private Config config;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "admin_stop_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean adminStop;

    @Builder
    public AdAccount(
//            User user,
            BusinessAccount businessAccount,
            String name,
            Config config,
            boolean adminStop) {
        this.businessAccount = businessAccount;
        this.name = name;
        this.config = config;
        this.adminStop = adminStop;
    }

    public AdAccount update(AdAccountDto.Request.Update request) {
        this.name = request.getName();
        return this;
    }

    public AdAccount delete(AdAccountDto.Request.Update request) {
        this.config = Config.DEL;
        return this;
    }

    public AdAccount addAdAccountUser(User user, AdAccountUser.MemberType memberType, AdAccountUser.Status status) {
        this.adAccountUsers.add(new AdAccountUser(this, user, memberType, status));
        return this;
    }

//    public AdAccount outOfBalanceUpdate(AdAccountDto.Request.OutOfBalanceUpdate request) {
//        this.outOfBalance = request.getOutOfBalance();
//        return this;
//    }

    public void changeConfigOn() {
        this.config = AdAccount.Config.ON;
    }
    public void changeConfigOff() {
        this.config = AdAccount.Config.OFF;
    }

    public void changeAdminStopOn() {
        this.adminStop = true;
        this.config = AdAccount.Config.OFF;
    }
    public void changeAdminStopOff() {
        this.adminStop = false;
    }
}
