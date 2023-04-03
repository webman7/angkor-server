package com.adplatform.restApi.global.config.security.dto;

import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.config.security.service.Aes256Service;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class TokenDto {
    private Integer userNo;
    private String userId;
    private String userName;
    private String userSite;
    private String accessToken;
    private String refreshToken;

    @SneakyThrows
    public static TokenDto create(Aes256Service aes256Service, User user, String userSite, String accessToken, String refreshToken) {
        return TokenDto.builder()
                .userNo(user.getId())
                .userId(aes256Service.encrypt(user.getLoginId()))
                .userName(aes256Service.encrypt(user.getName()))
                .userSite(aes256Service.encrypt(userSite))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
