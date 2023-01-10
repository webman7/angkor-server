package com.adplatform.restApi.domain.history.dto.campaign;

import com.adplatform.restApi.domain.history.dao.campaign.CampaignBudgetChangeHistoryRepository;
import com.adplatform.restApi.domain.history.domain.CampaignBudgetChangeHistory;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.global.dto.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = BaseMapperConfig.class)
public abstract class CampaignBudgetChangeHistoryMapper {

    @Mapping(target = "createdUserId", source = "loginUserId")
    public abstract CampaignBudgetChangeHistory toEntity(CampaignBudgetChangeHistoryDto.Request.Save SaveDto, Integer loginUserId);
}
