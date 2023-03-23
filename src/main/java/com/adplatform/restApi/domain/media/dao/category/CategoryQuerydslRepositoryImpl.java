package com.adplatform.restApi.domain.media.dao.category;

import com.adplatform.restApi.domain.media.dto.category.CategoryDto;
import com.adplatform.restApi.domain.media.dto.category.QCategoryDto_Response_Search;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.adplatform.restApi.domain.media.domain.QCategory.category;
import static com.adplatform.restApi.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class CategoryQuerydslRepositoryImpl implements CategoryQuerydslRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<CategoryDto.Response.Search> search(Pageable pageable) {
        List<CategoryDto.Response.Search> content = this.query.select(
                    new QCategoryDto_Response_Search(
                            category.id,
                            category.name,
                            user.loginId,
                            category.createdAt
                    )
                )
                .from(category, user)
                .where(category.deleted.eq(false),
                        category.createdUserNo.eq(user.id)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(category.count())
                .from(category)
                .where(category.deleted.eq(false)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
