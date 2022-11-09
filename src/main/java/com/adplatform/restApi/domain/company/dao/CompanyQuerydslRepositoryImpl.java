package com.adplatform.restApi.domain.company.dao;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.QCompanyDto_Response_Default;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Repository
public class CompanyQuerydslRepositoryImpl implements CompanyQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<CompanyDto.Response.Default> search(Pageable pageable, CompanyDto.Request.Search searchRequest) {
        List<CompanyDto.Response.Default> content = this.query
                .select(new QCompanyDto_Response_Default(company.id, company.name))
                .from(company)
                .where(
                        this.nameContains(searchRequest.getName()),
                        this.typeEq(searchRequest.getType()),
                        this.isDeletedEq(searchRequest.getDeleted()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(company.count())
                .where(
                        this.nameContains(searchRequest.getName()),
                        this.typeEq(searchRequest.getType()),
                        this.isDeletedEq(searchRequest.getDeleted()))
                .from(company);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<CompanyDto.Response.Default> searchForSignUp(Company.Type type, String name) {
        return this.query.select(new QCompanyDto_Response_Default(company.id, company.name))
                .from(company)
                .where(company.deleted.eq(false), company.type.eq(type), this.nameContains(name))
                .fetch();
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? company.name.contains(name) : null;
    }

    private BooleanExpression typeEq(Company.Type type) {
        return nonNull(type) ? company.type.eq(type) : null;
    }

    private BooleanExpression isDeletedEq(Boolean deleted) {
        if (deleted == null) return null;
        return deleted ? company.deleted.eq(true) : company.deleted.eq(false);
    }
}
