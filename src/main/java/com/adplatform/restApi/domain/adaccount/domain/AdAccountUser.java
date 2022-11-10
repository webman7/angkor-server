package com.adplatform.restApi.domain.adaccount.domain;

import com.adplatform.restApi.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "adaccount_user_info")
public class AdAccountUser {
    public enum MemberType {
        MASTER,
        OPERATOR,
        MEMBER
    }

    public enum RequestStatus {
        /** 수락 */
        Y,
        /** 요청 */
        N,
        /** 거부 */
        R,
        /** 요청 취소 */
        C
    }

    @EmbeddedId
    private AdAccountUserId id;

    @MapsId("adAccountId")
    @ManyToOne
    @JoinColumn(name = "adaccount_info_id")
    private AdAccount adAccount;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", length = 10)
    private MemberType memberType;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", columnDefinition = "CHAR")
    private RequestStatus requestStatus;

    @CreatedBy
    @Column(name = "reg_user_no")
    private Integer createdUserId;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "reg_date", updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "upd_user_no")
    private Integer updatedUserId;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "upd_date")
    private LocalDateTime updatedAt;
}
