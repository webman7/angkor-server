package com.adplatform.restApi.infra.mail.util;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author junny
 * @since 1.0
 */
public class EmailMessageUtil {
    public String getFindPasswordMessage(String randomPassword) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("email/find-password.html").getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
            }
            return stringBuilder.toString().replace("{randomPassword}", randomPassword);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getCause());
        }
    }

    public String getMediaCompanyInviteMessage(String inviteUserName, String companyName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ClassPathResource("email/find-password.html").getInputStream(), StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
            }
            return stringBuilder.toString().replace("{inviteUserName}", inviteUserName).replace("{companyName}", companyName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getCause());
        }
    }


}
