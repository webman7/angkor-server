package com.adplatform.restApi.infra.mail.event;

import com.adplatform.restApi.infra.mail.service.EmailService;
import com.adplatform.restApi.infra.mail.util.EmailMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Email event Handler Class.
 *
 * @author junny
 * @since 1.0
 * @see com.adplatform.restApi.infra.mail.event.FindPasswordEmailSentEvent FindPasswordEmailSentEvent
 */
@RequiredArgsConstructor
@Component
public class EmailEventHandler {
    private final EmailService emailService;

    @Async
    @EventListener(FindPasswordEmailSentEvent.class)
    public void handleFindPasswordEmailSentEvent(FindPasswordEmailSentEvent event) {
        String subject = "[Angkor AD] 인증번호 안내";
        this.emailService.sendEmail(
                event.getEmail(),
                subject,
                new EmailMessageUtil().getFindPasswordMessage(event.getRandomPassword()),
                true);
    }

    @Async
    @EventListener(MediaCompanyInviteEmailSentEvent.class)
    public void handleMediaCompanyInviteEmailSentEvent(MediaCompanyInviteEmailSentEvent event) {
        String subject = "[Angkor AD] 초대 메일";
        this.emailService.sendEmail(
                event.getEmail(),
                subject,
                new EmailMessageUtil().getMediaCompanyInviteMessage(event.getInviteUserName(), event.getCompanyName()),
                true);
    }
}
