package com.adplatform.restApi.domain.media.service;

import com.adplatform.restApi.domain.advertiser.adgroup.service.AdGroupFindUtils;
import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.media.dao.category.CategoryRepository;
import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.domain.media.dto.category.CategoryMapper;
import com.adplatform.restApi.domain.media.exception.CategoryUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CategorySaveService {

    CategoryRepository categoryRepository;

    CategoryMapper categoryMapper;

    public void save(CategoryDto.Request.Save request) {
        Category category = this.categoryMapper.toEntity(request);
        this.categoryRepository.save(category);
    }

    public void update(CategoryDto.Request.Update request) {
        try{
            Category category = CategoryFindUtils.findByIdOrElseThrow(request.getId(), this.categoryRepository).update(request);
        }catch (Exception e){
            throw new CategoryUpdateException();
        }
    }

    public void delete(Integer id) {
        CategoryFindUtils.findByIdOrElseThrow(id, this.categoryRepository).delete();
    }
}
