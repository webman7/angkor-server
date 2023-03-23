package com.adplatform.restApi.domain.media.api;

import com.adplatform.restApi.domain.media.dao.category.CategoryRepository;
import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.domain.media.dto.category.CategoryMapper;
import com.adplatform.restApi.domain.media.exception.CategoryNotFoundException;
import com.adplatform.restApi.domain.media.service.CategoryFindUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryQueryApi {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("search")
    public PageDto<CategoryDto.Response.CategoryInfo> search(
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.categoryRepository.search(pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CategoryDto.Response.CategoryInfo findById(@PathVariable Integer id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        return this.categoryMapper.toResponse(CategoryFindUtils.findByIdOrElseThrow(id, this.categoryRepository));
    }
}
