package com.adplatform.restApi.domain.adgroup.domain;

import com.adplatform.restApi.domain.adgroup.dto.schedule.AdGroupScheduleDto;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DynamicInsert
@Table(name = "adgroup_schedule")
public class AdGroupSchedule {
    @Id
    @Column(name = "adgroup_info_id", nullable = false, unique = true)
    private Integer id;

    @Setter
    @MapsId
    @OneToOne
    @JoinColumn(name = "adgroup_info_id")
    private AdGroup adGroup;

    @Column(name = "start_date")
    private Integer startDate;

    @Column(name = "end_date", columnDefinition = "int DEFAULT 29991231")
    private Integer endDate;

    /**
     * 심야 타겟팅 여부.<br/>
     * {@link Boolean#TRUE true}: 심야 시간대에만 타겟팅을 하며, 각 요일의 상세 시간 설정은 필요하지 않다.<br/>
     * {@link Boolean#FALSE false}: 심야 타겟팅을 하지 않는다. 각 요일의 상세 시간 설정이 필요하다.
     */
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "late_night_yn", columnDefinition = "CHAR")
    private boolean lateNightTargeting;

    /**
     * 상세 시간 설정 여부.<br/>
     * {@link Boolean#TRUE true}: 심야 타겟팅이 true인 경우 해당 값은 false이어야 한다.<br/>
     * {@link Boolean#FALSE false}: 심야 타겟팅이 false인 경우 해당 값은 true이어야 한다.
     */
    @Accessors(fluent = true)
    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "detail_time_yn", columnDefinition = "CHAR")
    private boolean hasDetailTime;

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "monday_time"))
    private ScheduleTime mondayTime;

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "tuesday_time"))
    private ScheduleTime tuesdayTime;

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "wednesday_time"))
    private ScheduleTime wednesdayTime;

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "thursdayTime"))
    private ScheduleTime thursdayTime;

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "friday_time"))
    private ScheduleTime fridayTime;

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "saturday_time"))
    private ScheduleTime saturdayTime;

    @Embedded
    @AttributeOverride(name = "time", column = @Column(name = "sunday_time"))
    private ScheduleTime sundayTime;

    @Builder
    public AdGroupSchedule(
            AdGroup adGroup,
            Integer startDate,
            Integer endDate,
            boolean lateNightTargeting,
            boolean hasDetailTime,
            ScheduleTime mondayTime,
            ScheduleTime tuesdayTime,
            ScheduleTime wednesdayTime,
            ScheduleTime thursdayTime,
            ScheduleTime fridayTime,
            ScheduleTime saturdayTime,
            ScheduleTime sundayTime) {
        this.adGroup = adGroup;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lateNightTargeting = lateNightTargeting;
        this.hasDetailTime = hasDetailTime;
        this.mondayTime = mondayTime;
        this.tuesdayTime = tuesdayTime;
        this.wednesdayTime = wednesdayTime;
        this.thursdayTime = thursdayTime;
        this.fridayTime = fridayTime;
        this.saturdayTime = saturdayTime;
        this.sundayTime = sundayTime;
    }

    public void update(AdGroupScheduleDto.Request.FirstSave request) {
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.lateNightTargeting = request.isLateNightTargeting();
        this.hasDetailTime = request.isHasDetailTime();
        this.mondayTime.update(request.getMondayTime());
        this.tuesdayTime.update(request.getTuesdayTime());
        this.wednesdayTime.update(request.getWednesdayTime());
        this.thursdayTime.update(request.getThursdayTime());
        this.fridayTime.update(request.getFridayTime());
        this.saturdayTime.update(request.getSaturdayTime());
        this.sundayTime.update(request.getSundayTime());
    }

    public AdGroupSchedule copy(AdGroup adGroup) {
        AdGroupSchedule copy = new AdGroupSchedule();
        copy.adGroup = adGroup;
        copy.startDate = this.startDate;
        copy.endDate = this.endDate;
        copy.lateNightTargeting = this.lateNightTargeting;
        copy.hasDetailTime = this.hasDetailTime;
        copy.mondayTime = this.mondayTime;
        copy.tuesdayTime = this.tuesdayTime;
        copy.wednesdayTime = this.wednesdayTime;
        copy.thursdayTime = this.thursdayTime;
        copy.fridayTime = this.fridayTime;
        copy.saturdayTime = this.saturdayTime;
        copy.sundayTime = this.sundayTime;
        return copy;
    }

    public void updateStartEndDate(Integer startDate, Integer endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
