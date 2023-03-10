package com.adplatform.restApi.domain.business.dao.account.mapper;

import com.adplatform.restApi.domain.agency.marketers.dto.AgencyMarketersDto;
import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface BusinessAccountQueryMapper {

    List<BusinessAccountDto.Response.MyAccounts> myAccounts(
            @Param("pageable") Pageable pageable, Integer id, String name, Integer loginUserNo
    );

    long countMyAccounts(Integer id, String name, Integer loginUserNo);
}
