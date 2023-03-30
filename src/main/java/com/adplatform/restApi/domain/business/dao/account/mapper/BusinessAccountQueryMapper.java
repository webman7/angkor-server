package com.adplatform.restApi.domain.business.dao.account.mapper;

import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface BusinessAccountQueryMapper {

    List<BusinessAccountDto.Response.Accounts> accounts(
            @Param("pageable") Pageable pageable, Integer id, String name, Integer loginUserNo
    );
    long countAccounts(Integer id, String name, Integer loginUserNo);

    List<BusinessAccountDto.Response.BusinessAdAccount> businessAdAccount(
            @Param("pageable") Pageable pageable, String searchType, String searchKeyword, Integer loginUserNo
    );
    long countBusinessAdAccount(String searchType, String searchKeyword, Integer loginUserNo);



    void updateRefundAccount(BusinessAccountDto.Request.UpdateRefundAccount request, Integer loginUserNo);
}
