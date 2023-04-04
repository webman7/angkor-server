package com.adplatform.restApi.domain.statistics.dto;

import com.adplatform.restApi.domain.company.domain.Company;
import com.adplatform.restApi.domain.media.domain.Category;
import com.adplatform.restApi.domain.media.domain.Media;
import com.adplatform.restApi.domain.media.dto.MediaDto;
import com.adplatform.restApi.domain.media.dto.MediaFileDto;
import com.adplatform.restApi.domain.statistics.domain.taxbill.MediaTaxBill;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public abstract class TaxBillMapper {
//    public abstract AdAccountTaxBill toEntityTaxBill(TaxBillDto.Request.Save SaveDto);


    @Mapping(target = "issueStatus", expression = "java(false)")
    public abstract MediaTaxBill toEntityMediaTaxBill(TaxBillDto.Request.Save SaveDto);


    @Mapping(target = "id", source = "mediaTaxBill.id")
    @Mapping(target = "mediaName", source = "media.name")
    @Mapping(target = "issueUserNo", source = "mediaTaxBill.issueUserNo")
    @Mapping(target = "paymentUserNo", source = "mediaTaxBill.paymentUserNo")
    @Mapping(target = "memo", source = "mediaTaxBill.memo")
    @Mapping(target = "mediaTaxBillFiles", source = "mediaTaxBillFile")
    public abstract TaxBillDto.Response.TaxBillInfo toResponse(MediaTaxBill mediaTaxBill, Media media, Company company, MediaTaxBillFileDto.Response.FileInfo mediaTaxBillFile);


}
