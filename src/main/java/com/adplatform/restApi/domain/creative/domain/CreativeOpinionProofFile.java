package com.adplatform.restApi.domain.creative.domain;

import com.adplatform.restApi.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.*;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "creative_opinion_proof")
public class CreativeOpinionProofFile extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "creative_info_id")
    private Creative creative;

    @Embedded
    private FileInformation information;

    public CreativeOpinionProofFile copy(Creative creative) {
        CreativeOpinionProofFile copy = new CreativeOpinionProofFile();
        copy.creative = creative;
        copy.information = SerializationUtils.clone(this.information);
        return copy;
    }
}
