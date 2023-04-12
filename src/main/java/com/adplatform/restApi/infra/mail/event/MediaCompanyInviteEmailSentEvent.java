package com.adplatform.restApi.infra.mail.event;

import com.adplatform.restApi.global.value.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MediaCompanyInviteEmailSentEvent {
    private final Email email;
    private final String inviteUserName;
    private final String companyName;
}
