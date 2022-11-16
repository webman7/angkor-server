package com.adplatform.restApi.global.config.security.dto;

import lombok.*;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDto {
    String accessToken;
    String refreshToken;

    @Builder
    public TokenRequestDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
