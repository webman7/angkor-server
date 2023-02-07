package com.adplatform.restApi.global.config.security.dto;

import com.adplatform.restApi.user.domain.User;
import com.adplatform.restApi.global.config.security.service.Aes256Service;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Builder(access = AccessLevel.PROTECTED)
public class TokenDto {
    private String userId;
    private String userName;
    private String accessToken;
    private String refreshToken;

    @SneakyThrows
    public static TokenDto create(Aes256Service aes256Service, User user, String accessToken, String refreshToken) {
        return TokenDto.builder()
                .userId(aes256Service.encrypt(user.getLoginId()))
                .userName(aes256Service.encrypt(user.getName()))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
