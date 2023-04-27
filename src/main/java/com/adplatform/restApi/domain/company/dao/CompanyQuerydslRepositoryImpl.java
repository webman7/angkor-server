package com.adplatform.restApi.domain.company.dao;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.company.dto.*;
import com.adplatform.restApi.domain.media.domain.Media;
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

import static com.adplatform.restApi.domain.adaccount.domain.QAdAccount.adAccount;
import static com.adplatform.restApi.domain.bank.domain.QBank.bank;
import static com.adplatform.restApi.domain.business.domain.QBusinessAccountUser.businessAccountUser;
import static com.adplatform.restApi.domain.company.domain.QCompany.company;
import static com.adplatform.restApi.domain.company.domain.QCompanyFile.companyFile;
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;
import static java.util.Objects.nonNull;

/**
 * @author junny
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
                .orderBy(company.id.desc())
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
    public List<CompanyDto.Response.Default> list() {
        return this.query
                .select(new QCompanyDto_Response_Default(company.id, company.name))
                .from(company)
                .where(
                        company.deleted.eq(false))
                .fetch();
    }

    @Override
    public List<CompanyDto.Response.Default> mediaCompanyList() {
        return this.query
                .select(new QCompanyDto_Response_Default(company.id, company.name))
                .from(company)
                .where(
                        company.type.eq(Company.Type.MEDIA),
                        company.deleted.eq(false))
                .fetch();
    }


    @Override
    public List<CompanyDto.Response.MediaByCompany> listMediaByCompany(CompanyDto.Request.MediaByCompany searchRequest) {
        return this.query
                .select(new QCompanyDto_Response_MediaByCompany(
                        company.id,
                        media.id,
                        media.name))
                .from(company, media)
                .where(
                        media.company.id.eq(company.id),
                        companyIdEq(searchRequest.getCompanyId()),
                        media.status.in(Media.Status.Y),
                        company.deleted.eq(false))
                .fetch();
    }


    @Override
    public Page<CompanyDto.Response.CompanyInfo> searchMedia(Pageable pageable, CompanyDto.Request.SearchMedia searchRequest) {
        List<CompanyDto.Response.CompanyInfo> content = this.query
                .select(new QCompanyDto_Response_CompanyInfo(
                        company.id,
                        company.name,
                        company.type,
                        company.registrationNumber,
                        company.representationName,
                        company.baseAddress,
                        company.detailAddress,
                        company.zipCode,
                        company.businessCategory,
                        company.businessItem,
                        company.taxBillEmail,
                        company.bank,
                        company.accountNumber,
                        company.accountOwner,
                        as(select(companyFile.information.url)
                                        .from(companyFile)
                                        .where(companyFile.company.id.eq(company.id),
                                                companyFile.type.eq("BUSINESS"),
                                                companyFile.id.eq(select(companyFile.id.max())
                                                        .from(companyFile)
                                                        .where(companyFile.company.id.eq(company.id),
                                                                companyFile.type.eq("BUSINESS")
                                                        )
                                                        .orderBy(companyFile.id.desc()))
                                        ), "businessFileUrl"),
                        as(select(companyFile.information.originalFileName)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS"),
                                        companyFile.id.eq(select(companyFile.id.max())
                                                .from(companyFile)
                                                .where(companyFile.company.id.eq(company.id),
                                                        companyFile.type.eq("BUSINESS")
                                                )
                                                .orderBy(companyFile.id.desc()))
                                ), "businessFileName"),
                        as(select(companyFile.information.fileType)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS"),
                                        companyFile.id.eq(select(companyFile.id.max())
                                                .from(companyFile)
                                                .where(companyFile.company.id.eq(company.id),
                                                        companyFile.type.eq("BUSINESS")
                                                )
                                                .orderBy(companyFile.id.desc()))
                                ), "businessFileType"),
                        as(select(companyFile.information.url)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK"),
                                        companyFile.id.eq(select(companyFile.id.max())
                                                        .from(companyFile)
                                                        .where(companyFile.company.id.eq(company.id),
                                                                companyFile.type.eq("BANK")
                                                        )
                                                        .orderBy(companyFile.id.desc()))
                                ), "bankFileUrl"),
                        as(select(companyFile.information.originalFileName)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK"),
                                        companyFile.id.eq(select(companyFile.id.max())
                                                .from(companyFile)
                                                .where(companyFile.company.id.eq(company.id),
                                                        companyFile.type.eq("BANK")
                                                )
                                                .orderBy(companyFile.id.desc()))
                                ), "bankFileName"),
                        as(select(companyFile.information.fileType)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK"),
                                        companyFile.id.eq(select(companyFile.id.max())
                                                .from(companyFile)
                                                .where(companyFile.company.id.eq(company.id),
                                                        companyFile.type.eq("BANK")
                                                )
                                                .orderBy(companyFile.id.desc()))
                                ), "bankFileType")
                ))
                .from(company)
                .leftJoin(company.bank, bank)
                .where(
                        this.nameContains(searchRequest.getName()),
                        this.registrationNumberEq(searchRequest.getRegistrationNumber()),
                        company.type.eq(Company.Type.MEDIA))
                .orderBy(company.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(company.count())
                .where(
                        this.nameContains(searchRequest.getName()),
                        this.registrationNumberEq(searchRequest.getRegistrationNumber()),
                        company.type.eq(Company.Type.MEDIA))
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
                        company.baseAddress,
                        company.detailAddress,
                        company.zipCode,
                        company.businessCategory,
                        company.businessItem,
                        company.taxBillEmail
                ))
                .from(company)
                .where(
                        this.registrationNumberEq(searchRequest.getSearchKeyword()),
                        this.typeEq(searchRequest.getType()))
                .orderBy(company.id.desc())
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
    public Integer registrationNumberCount(CompanyDto.Request.SearchKeyword searchRequest) {
        List<CompanyDto.Response.CompanyInfo> content = this.query
                .select(new QCompanyDto_Response_CompanyInfo(
                        company.id,
                        company.name,
                        company.type,
                        company.registrationNumber,
                        company.representationName,
                        company.baseAddress,
                        company.detailAddress,
                        company.zipCode,
                        company.businessCategory,
                        company.businessItem,
                        company.taxBillEmail,
                        company.bank,
                        company.accountNumber,
                        company.accountOwner,
                        as(select(companyFile.information.url)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "businessFileUrl"),
                        as(select(companyFile.information.originalFileName)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "businessFileName"),
                        as(select(companyFile.information.fileType)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "businessFileType"),
                        as(select(companyFile.information.url)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "bankFileUrl"),
                        as(select(companyFile.information.originalFileName)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "bankFileName"),
                        as(select(companyFile.information.fileType)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "bankFileType")
                ))
                .from(company)
                .leftJoin(company.bank, bank)
                .where(
                        this.registrationNumberEq(searchRequest.getSearchKeyword()),
                        this.typeEq(searchRequest.getType()))
                .fetch();

        return content.size();
    }

    @Override
    public Integer findByRegistrationNumberCount(String registrationNumber) {
        List<CompanyDto.Response.CompanyInfo> content = this.query
                .select(new QCompanyDto_Response_CompanyInfo(
                        company.id,
                        company.name,
                        company.type,
                        company.registrationNumber,
                        company.representationName,
                        company.baseAddress,
                        company.detailAddress,
                        company.zipCode,
                        company.businessCategory,
                        company.businessItem,
                        company.taxBillEmail,
                        company.bank,
                        company.accountNumber,
                        company.accountOwner,
                        as(select(companyFile.information.url)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "businessFileUrl"),
                        as(select(companyFile.information.originalFileName)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "businessFileName"),
                        as(select(companyFile.information.fileType)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BUSINESS")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "businessFileType"),
                        as(select(companyFile.information.url)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "bankFileUrl"),
                        as(select(companyFile.information.originalFileName)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "bankFileName"),
                        as(select(companyFile.information.fileType)
                                .from(companyFile)
                                .where(companyFile.company.id.eq(company.id),
                                        companyFile.type.eq("BANK")
                                )
                                .orderBy(companyFile.id.desc())
                                .limit(1), "bankFileType")
                ))
                .from(company)
                .leftJoin(company.bank, bank)
                .where(
                        this.registrationNumberEq(registrationNumber)
                )
                .fetch();

        return content.size();
    }

//    @Override
//    public List<CompanyFileDto.Response.Default> findDetailFilesById(Integer id) {
//        return this.query.select(new QCompanyFileDto_Response_Default(
//                companyFile.id,
//                companyFile.company.id,
//                companyFile.type,
//                companyFile.information.fileSize,
//                companyFile.information.filename,
//                companyFile.information.originalFileName,
//                companyFile.information.url,
//                companyFile.information.mimeType
//                ))
//                .from(company, companyFile)
//                .where(company.id.eq(id),
//                        company.id.eq(companyFile.company.id))
//                .fetch();
//    }

    @Override
    public CompanyFileDto.Response.Default findDetailBusinessFilesById(Integer id) {
        return this.query.select(new QCompanyFileDto_Response_Default(
                        companyFile.id,
                        companyFile.company.id,
                        companyFile.type,
                        companyFile.information.fileType,
                        companyFile.information.fileSize,
                        companyFile.information.filename,
                        companyFile.information.originalFileName,
                        companyFile.information.url,
                        companyFile.information.mimeType
                ))
                .from(company, companyFile)
                .where(company.id.eq(id),
                        companyFile.type.eq("BUSINESS"),
                        company.id.eq(companyFile.company.id))
                .orderBy(companyFile.id.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public CompanyFileDto.Response.Default findDetailBankFilesById(Integer id) {
        return this.query.select(new QCompanyFileDto_Response_Default(
                        companyFile.id,
                        companyFile.company.id,
                        companyFile.type,
                        companyFile.information.fileType,
                        companyFile.information.fileSize,
                        companyFile.information.filename,
                        companyFile.information.originalFileName,
                        companyFile.information.url,
                        companyFile.information.mimeType
                ))
                .from(company, companyFile)
                .where(company.id.eq(id),
                        companyFile.type.eq("BANK"),
                        company.id.eq(companyFile.company.id))
                .orderBy(companyFile.id.desc())
                .limit(1)
                .fetchOne();
    }

















    @Override
    public List<CompanyDto.Response.Default> searchForSignUp(Company.Type type, String name) {
        return this.query.select(new QCompanyDto_Response_Default(company.id, company.name))
                .from(company)
                .where(company.deleted.eq(false), company.type.eq(type), this.nameContains(name))
                .fetch();
    }

    private BooleanExpression companyIdEq(Integer id) {
        return id != null ? company.id.eq(id) : null;
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
        return StringUtils.hasText(registrationNumber) ? company.registrationNumber.eq(registrationNumber) : null;
    }
}
