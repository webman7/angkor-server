package com.adplatform.restApi.domain.campaign.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ad_type_goal_info")
public class AdTypeAndGoal extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_type_info_id")
    private AdType adType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_goal_info_id")
    private AdGoal adGoal;
}
