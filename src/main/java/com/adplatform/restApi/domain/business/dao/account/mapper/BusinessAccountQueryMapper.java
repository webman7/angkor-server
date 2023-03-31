package com.adplatform.restApi.domain.business.dao.account.mapper;

import com.adplatform.restApi.domain.business.dto.account.BusinessAccountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface BusinessAccountQueryMapper {

    List<BusinessAccountDto.Response.Accounts> accounts(
            @Param("id") Integer id, @Param("name") String name, @Param("loginUserNo") Integer loginUserNo, @Param("pageable") Pageable pageable
    );
    long countAccounts(@Param("id") Integer id, @Param("name") String name, @Param("loginUserNo") Integer loginUserNo);

    List<BusinessAccountDto.Response.BusinessAdAccount> businessAdAccount(
            String searchType, String searchKeyword, Integer loginUserNo, @Param("pageable") Pageable pageable
    );
    long countBusinessAdAccount(@Param("searchType") String searchType, @Param("searchKeyword") String searchKeyword, @Param("loginUserNo") Integer loginUserNo);



    void updateRefundAccount(BusinessAccountDto.Request.UpdateRefundAccount request, @Param("loginUserNo") Integer loginUserNo);
}
