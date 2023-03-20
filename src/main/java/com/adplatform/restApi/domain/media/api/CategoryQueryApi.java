package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.media.dao.category.CategoryRepository;
import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryQueryApi {

    private final CategoryRepository categoryRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("search")
    public PageDto<CategoryDto.Response.CategoryInfo> search(
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.categoryRepository.search(pageable));
    }
}
