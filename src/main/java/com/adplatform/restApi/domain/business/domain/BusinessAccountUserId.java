package com.adplatform.restApi.domain.business.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BusinessAccountUserId implements Serializable {
    @Column(name = "business_account_info_id")
    private Integer businessAccountId;

    @Column(name = "user_no")
    private Integer userId;
}
