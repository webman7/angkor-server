package com.adplatform.restApi.domain.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class AdminUserId implements Serializable {
    @Column(name = "company_info_id")
    private Integer companyId;

    @Column(name = "user_no")
    private Integer userId;
}
