package com.adplatform.restApi.advertiser.company.dao;

import com.adplatform.restApi.advertiser.company.domain.Company;
import com.adplatform.restApi.advertiser.company.dto.CompanyDto;
import com.adplatform.restApi.advertiser.company.dto.QCompanyDto_Response_AdAccountDetail;
import com.adplatform.restApi.advertiser.company.dto.QCompanyDto_Response_Default;
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

import static com.adplatform.restApi.advertiser.company.domain.QCompany.company;
import static java.util.Objects.nonNull;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
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
    public Page<CompanyDto.Response.AdAccountDetail> registrationNumber(Pageable pageable, CompanyDto.Request.SearchKeyword searchRequest) {
        List<CompanyDto.Response.AdAccountDetail> content = this.query
                .select(new QCompanyDto_Response_AdAccountDetail(
                        company.id,
                        company.name,
                        company.type,
                        company.registrationNumber,
                        company.representationName,
                        company.address,
                        company.businessCategory,
                        company.businessItem,
                        company.taxBillEmail1,
                        company.taxBillEmail2
                ))
                .from(company)
                .where(
                        this.registrationNumberEq(searchRequest.getSearchKeyword()),
                        this.typeEq(searchRequest.getType()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(company.count())
                .where(
                        this.registrationNumberEq(searchRequest.getSearchKeyword()),
                        this.typeEq(searchRequest.getType()))
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

    private BooleanExpression registrationNumberEq(String registrationNumber) {
        return nonNull(registrationNumber) ? company.registrationNumber.eq(registrationNumber) : null;
    }
}