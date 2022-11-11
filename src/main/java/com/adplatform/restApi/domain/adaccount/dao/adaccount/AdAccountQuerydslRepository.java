package com.adplatform.restApi.domain.adaccount.dao.adaccount;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.dto.adaccount.AdAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdAccountQuerydslRepository {
    Page<AdAccountDto.Response.Page> search(Pageable pageable, AdAccountDto.Request.MySearch request, Integer userId);

    Page<AdAccountDto.Response.ForAdvertiser> searchForAdvertiser(
            Pageable pageable, Integer id, String name, Integer loginUserId, AdAccountUser.RequestStatus requestStatus);
}
