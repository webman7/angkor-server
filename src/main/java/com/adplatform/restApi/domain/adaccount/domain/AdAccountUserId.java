package com.adplatform.restApi.domain.adaccount.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AdAccountUserId implements Serializable {
    @Column(name = "adaccount_info_id")
    private Integer adAccountId;

    @Column(name = "user_no")
    private Integer userId;
}
