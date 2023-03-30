package com.adplatform.restApi.domain.history.domain;

import com.adplatform.restApi.global.entity.BaseCreatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "admin_stop_history")
public class AdminStopHistory extends BaseCreatedEntity {
    public enum Type {
        ADACCOUNT, CAMPAIGN, ADGROUP, CREATIVE
    }

//    public enum Reason {
//        CONTENTS_ERROR, AD_INCONGRUITY
//    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50)
    private AdminStopHistory.Type type;

    @Column(name = "stop_id", nullable = false)
    private Integer stopId;

    @Column(name = "reason")
    private String reason;

    @Builder
    public AdminStopHistory(
            AdminStopHistory.Type type,
            Integer stopId,
            String reason) {
        this.type = type;
        this.stopId = stopId;
        this.reason = reason;
    }
}
