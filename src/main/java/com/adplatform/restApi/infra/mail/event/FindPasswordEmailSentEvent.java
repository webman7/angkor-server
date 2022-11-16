package com.adplatform.restApi.infra.mail.event;

import com.adplatform.restApi.global.value.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class FindPasswordEmailSentEvent {
    private final Email email;
    private final String randomPassword;
}
