package com.adplatform.restApi.domain.media.domain;

import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.entity.BaseUpdatedEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "category_info")
public class Category extends BaseUpdatedEntity {

    @Column(name = "name")
    private String name;

    @Convert(converter = BooleanToStringYOrNConverter.class)
    @Column(name = "del_yn", nullable = false, columnDefinition = "CHAR(1)")
    private boolean deleted;

    @Builder
    public Category(String name) {
        this.name = name;
        this.deleted = false;
    }

    public Category update(CategoryDto.Request.Update request) {
        this.name = request.getName();
        return this;
    }

    public void delete() {
        this.deleted = true;
    }
}