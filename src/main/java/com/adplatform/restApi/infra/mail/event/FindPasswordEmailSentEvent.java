package com.adplatform.restApi.infra.mail.event;

import com.adplatform.restApi.global.value.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindPasswordEmailSentEvent {
    private final Email email;
    private final String randomPassword;
}
