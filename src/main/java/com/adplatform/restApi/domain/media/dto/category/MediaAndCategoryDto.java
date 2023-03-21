package com.adplatform.restApi.domain.media.dto.category;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class MediaAndCategoryDto {
    @NotBlank
    private Integer mediaId;
    @NotBlank
    private Integer categoryId;

    @QueryProjection
    public MediaAndCategoryDto(Integer mediaId, Integer categoryId) {
        this.mediaId = mediaId;
        this.categoryId = categoryId;
    }
}
