package com.adplatform.restApi.domain.adgroup.domain;

import com.adplatform.restApi.domain.adgroup.converter.TimeBooleanListToStringConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ScheduleTime {
    @Convert(converter = TimeBooleanListToStringConverter.class)
    @Column(name = "time", length = 100)
    private List<Boolean> time;

    public ScheduleTime(List<Boolean> time) {
        this.time = Collections.unmodifiableList(time);
    }

    public void update(List<Boolean> time) {
        this.time = Collections.unmodifiableList(time);
    }
}
