package com.adplatform.restApi.domain.adgroup.domain;

import com.adplatform.restApi.domain.adgroup.dto.target.AdGroupDemographicTargetDto;
import com.adplatform.restApi.global.constant.Gender;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.converter.StringListToStringConverter;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "adgroup_targeting_demographic")
public class AdGroupDemographicTarget {
    /**
     * 청중 타입
     */
    public enum Type {
        /** 일반 타겟팅 설정 */
        NORMAL,
        /** 디스플레이 오디언스 관리 기능 설정 */
        DISPLAY
    }

    @Id
    @Column(name = "adgroup_info_id")
    private Integer id;

    @Setter
    @MapsId
    @OneToOne
    @JoinColumn(name = "adgroup_info_id")
    private AdGroup adGroup;

    /**
     * 전체 연령 여부.<br/>
     * {@link Boolean#TRUE true}: 전체 연령을 타겟으로 한다.<br/>
     * {@link Boolean#FALSE false}: 일부 연령을 타겟으로 한다.
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "age_all_yn", columnDefinition = "CHAR")
    private boolean allAge;

    @Convert(converter = StringListToStringConverter.class)
    @Column(name = "ages", length = 100)
    private List<String> ages;

    /**
     * 전체 성별 여부.<br/>
     * {@link Boolean#TRUE true}: 전체 성별을 대상으로 한다.<br/>
     * {@link Boolean#FALSE false}: 일부 성별을 대상으로 한다.
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "gender_all_yn", columnDefinition = "CHAR")
    private boolean allGender;

    @Enumerated(EnumType.STRING)
    @Column(name = "genders", length = 15)
    private Gender gender;

    @Builder
    public AdGroupDemographicTarget(
            AdGroup adGroup,
            boolean allAge,
            List<String> ages,
            boolean allGender,
            Gender gender) {
        this.adGroup = adGroup;
        this.allAge = allAge;
        this.ages = ages;
        this.allGender = allGender;
        this.gender = gender;
    }

    public void update(AdGroupDemographicTargetDto.Request.FirstSave request) {
        this.allAge = request.isAllAge();
        this.ages = request.getAges();
        this.allGender = request.isAllGender();
        this.gender = request.getGender();
    }
}
