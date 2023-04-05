package com.adplatform.restApi.domain.statistics.dao.taxbill;

import com.adplatform.restApi.domain.company.dto.QCompanyDto_Response_Default;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.MediaFileDto;
import com.adplatform.restApi.domain.media.dto.QMediaDto_Response_Search;
import com.adplatform.restApi.domain.media.dto.QMediaFileDto_Response_FileInfo;
import com.adplatform.restApi.domain.statistics.dto.*;
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
import static com.adplatform.restApi.domain.media.domain.QMedia.media;
import static com.adplatform.restApi.domain.statistics.domain.taxbill.QMediaTaxBill.mediaTaxBill;
import static com.adplatform.restApi.domain.statistics.domain.taxbill.QMediaTaxBillFile.mediaTaxBillFile;
import static com.adplatform.restApi.domain.statistics.domain.taxbill.QMediaTaxBillPaymentFile.mediaTaxBillPaymentFile;
import static com.adplatform.restApi.domain.user.domain.QUser.user;
import static com.querydsl.core.types.ExpressionUtils.as;
import static com.querydsl.jpa.JPAExpressions.select;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Repository
public class MediaTaxBillQuerydslRepositoryImpl implements MediaTaxBillQuerydslRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<TaxBillDto.Response.TaxBill> searchTax(Pageable pageable, TaxBillDto.Request.SearchTax searchRequest) {
        List<TaxBillDto.Response.TaxBill> content = this.query.select(
                        new QTaxBillDto_Response_TaxBill(
                                mediaTaxBill.id,
                                mediaTaxBill.mediaId,
                                media.name,
                                mediaTaxBill.companyId,
                                company.name,
                                mediaTaxBill.statDate,
                                mediaTaxBill.supplyAmount,
                                mediaTaxBill.vatAmount,
                                mediaTaxBill.totalAmount,
                                mediaTaxBill.memo,
                                as(select(mediaTaxBillFile.information.url)
                                        .from(mediaTaxBillFile)
                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                                                mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                                        .from(mediaTaxBillFile)
                                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                                        ))
                                        ), "mediaTaxBillFileUrl"),
                                as(select(mediaTaxBillFile.information.originalFileName)
                                        .from(mediaTaxBillFile)
                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                                                mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                                        .from(mediaTaxBillFile)
                                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                                        ))
                                        ), "mediaTaxBillFileName"),
                                as(select(mediaTaxBillFile.information.fileType)
                                        .from(mediaTaxBillFile)
                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                                                mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                                        .from(mediaTaxBillFile)
                                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                                        ))
                                        ), "mediaTaxBillFileType"),
                                mediaTaxBill.issueStatus,
                                mediaTaxBill.issueUserNo,
                                as(select(user.loginId)
                                        .from(user)
                                        .where(user.id.eq(mediaTaxBill.issueUserNo)), "issueUserId"),
                                mediaTaxBill.issueAt,
                                mediaTaxBill.paymentStatus,
                                mediaTaxBill.paymentUserNo,
                                as(select(user.loginId)
                                        .from(user)
                                        .where(user.id.eq(mediaTaxBill.paymentUserNo)), "paymentUserId"),
                                mediaTaxBill.paymentAt)
                )
                .from(mediaTaxBill, company, media)
                .leftJoin(mediaTaxBillFile)
                .on(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                        mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                .from(mediaTaxBillFile)
                                .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                )))
                .where(
                        mediaTaxBill.mediaId.eq(media.id),
                        mediaTaxBill.companyId.eq(company.id),
                        media.company.id.eq(company.id),
                        this.companyIdEq(searchRequest.getCompanyId()),
                        this.containsCompanyName(searchRequest.getCompanyName()),
                        this.containsMediaName(searchRequest.getMediaName()),
                        mediaTaxBill.statDate.between(
                                Integer.parseInt(searchRequest.getStartDate()),
                                Integer.parseInt(searchRequest.getEndDate())
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(mediaTaxBill.count())
                .from(mediaTaxBill, company, media)
                .leftJoin(mediaTaxBillFile)
                .on(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                        mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                .from(mediaTaxBillFile)
                                .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                )))
                .where(
                        mediaTaxBill.mediaId.eq(media.id),
                        mediaTaxBill.companyId.eq(company.id),
                        media.company.id.eq(company.id),
                        this.companyIdEq(searchRequest.getCompanyId()),
                        this.containsCompanyName(searchRequest.getCompanyName()),
                        this.containsMediaName(searchRequest.getMediaName()),
                        mediaTaxBill.statDate.between(
                                Integer.parseInt(searchRequest.getStartDate()),
                                Integer.parseInt(searchRequest.getEndDate())
                        )
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<TaxBillDto.Response.TaxBill> searchSettlement(Pageable pageable, TaxBillDto.Request.SearchTax searchRequest) {
        List<TaxBillDto.Response.TaxBill> content = this.query.select(
                        new QTaxBillDto_Response_TaxBill(
                                mediaTaxBill.id,
                                mediaTaxBill.mediaId,
                                media.name,
                                mediaTaxBill.companyId,
                                company.name,
                                mediaTaxBill.statDate,
                                mediaTaxBill.supplyAmount,
                                mediaTaxBill.vatAmount,
                                mediaTaxBill.totalAmount,
                                mediaTaxBill.memo,
                                as(select(mediaTaxBillFile.information.url)
                                        .from(mediaTaxBillFile)
                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                                                mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                                        .from(mediaTaxBillFile)
                                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                                        ))
                                        ), "mediaTaxBillFileUrl"),
                                as(select(mediaTaxBillFile.information.originalFileName)
                                        .from(mediaTaxBillFile)
                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                                                mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                                        .from(mediaTaxBillFile)
                                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                                        ))
                                        ), "mediaTaxBillFileName"),
                                as(select(mediaTaxBillFile.information.fileType)
                                        .from(mediaTaxBillFile)
                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                                                mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                                        .from(mediaTaxBillFile)
                                                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                                        ))
                                        ), "mediaTaxBillFileType"),
                                mediaTaxBill.issueStatus,
                                mediaTaxBill.issueUserNo,
                                as(select(user.loginId)
                                        .from(user)
                                        .where(user.id.eq(mediaTaxBill.issueUserNo)), "issueUserId"),
                                mediaTaxBill.issueAt,
                                mediaTaxBill.paymentStatus,
                                mediaTaxBill.paymentUserNo,
                                as(select(user.loginId)
                                        .from(user)
                                        .where(user.id.eq(mediaTaxBill.paymentUserNo)), "paymentUserId"),
                                mediaTaxBill.paymentAt)
                )
                .from(mediaTaxBill, company, media)
                .leftJoin(mediaTaxBillFile)
                .on(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                        mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                .from(mediaTaxBillFile)
                                .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                )))
                .where(
                        mediaTaxBill.mediaId.eq(media.id),
                        mediaTaxBill.companyId.eq(company.id),
                        media.company.id.eq(company.id),
                        this.companyIdEq(searchRequest.getCompanyId()),
                        this.containsCompanyName(searchRequest.getCompanyName()),
                        this.containsMediaName(searchRequest.getMediaName()),
                        mediaTaxBill.issueStatus.eq(true),
                        mediaTaxBill.statDate.between(
                                Integer.parseInt(searchRequest.getStartDate()),
                                Integer.parseInt(searchRequest.getEndDate())
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = this.query.select(mediaTaxBill.count())
                .from(mediaTaxBill, company, media)
                .leftJoin(mediaTaxBillFile)
                .on(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id),
                        mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                                .from(mediaTaxBillFile)
                                .where(mediaTaxBillFile.mediaTaxBill.id.eq(mediaTaxBill.id)
                                )))
                .where(
                        mediaTaxBill.mediaId.eq(media.id),
                        mediaTaxBill.companyId.eq(company.id),
                        media.company.id.eq(company.id),
                        this.companyIdEq(searchRequest.getCompanyId()),
                        this.containsCompanyName(searchRequest.getCompanyName()),
                        this.containsMediaName(searchRequest.getMediaName()),
                        mediaTaxBill.statDate.between(
                                Integer.parseInt(searchRequest.getStartDate()),
                                Integer.parseInt(searchRequest.getEndDate())
                        )
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public MediaTaxBillFileDto.Response.FileInfo findByMediaIdFileInfo(Integer id) {
        return this.query.select(new QMediaTaxBillFileDto_Response_FileInfo(
                        mediaTaxBillFile.information.fileType,
                        mediaTaxBillFile.information.fileSize,
                        mediaTaxBillFile.information.filename,
                        mediaTaxBillFile.information.originalFileName,
                        mediaTaxBillFile.information.url,
                        mediaTaxBillFile.information.mimeType
                ))
                .from(mediaTaxBillFile)
                .where(mediaTaxBillFile.id.eq(select(mediaTaxBillFile.id.max())
                        .from(mediaTaxBillFile)
                        .where(mediaTaxBillFile.mediaTaxBill.id.eq(id)
                        )))
                .fetchOne();
    }

    @Override
    public MediaTaxBillPaymentFileDto.Response.FileInfo findByMediaIdPaymentFileInfo(Integer id) {
        return this.query.select(new QMediaTaxBillPaymentFileDto_Response_FileInfo(
                        mediaTaxBillPaymentFile.information.fileType,
                        mediaTaxBillPaymentFile.information.fileSize,
                        mediaTaxBillPaymentFile.information.filename,
                        mediaTaxBillPaymentFile.information.originalFileName,
                        mediaTaxBillPaymentFile.information.url,
                        mediaTaxBillPaymentFile.information.mimeType
                ))

                .from(mediaTaxBillPaymentFile)
                .where(mediaTaxBillPaymentFile.id.eq(select(mediaTaxBillPaymentFile.id.max())
                        .from(mediaTaxBillPaymentFile)
                        .where(mediaTaxBillPaymentFile.mediaTaxBill.id.eq(id)
                        )))
                .fetchOne();
    }


    private BooleanExpression companyIdEq(Integer companyId) {
        return nonNull(companyId) && !companyId.equals(0) ? mediaTaxBill.companyId.eq(companyId) : null;
    }

    private BooleanExpression containsCompanyName(String name) {
        return StringUtils.hasText(name) ? company.name.contains(name) : null;
    }
    private BooleanExpression containsMediaName(String name) {
        return StringUtils.hasText(name) ? media.name.contains(name) : null;
    }
}
