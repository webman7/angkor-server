package com.adplatform.restApi.domain.user.domain;

import com.adplatform.restApi.domain.user.exception.PasswordWrongCountExceededException;
import com.adplatform.restApi.global.converter.BooleanToStringYOrNConverter;
import com.adplatform.restApi.global.util.RandomCodeGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Password {
    private static final int MAX_WRONG_COUNT = 5;

    @Column(name = "user_password", nullable = false, length = 128)
    private String value;

    @Column(name = "pwd_wrong_cnt")
    private int wrongCount;

//    @Convert(converter = BooleanToStringYOrNConverter.class)
//    @Column(name = "first_pwd_yn", columnDefinition = "CHAR")
//    private boolean needChange;

    @LastModifiedDate
    @Column(name = "pwd_upd_date")
    private LocalDateTime updatedAt;

    public Password(String password) {
        this.value = password;
        this.wrongCount = 0;
    }

    public boolean validate(PasswordEncoder passwordEncoder, String rawPassword) {
        this.validateMaxWrongCount();
        if (!passwordEncoder.matches(rawPassword, this.value)) {
            this.wrongCount++;
            this.validateMaxWrongCount();
            return false;
        }
        this.wrongCount = 0;
        return true;
    }

    private void validateMaxWrongCount() {
        if (this.wrongCount >= MAX_WRONG_COUNT) throw new PasswordWrongCountExceededException();
    }

    public String changeToRandomPassword(PasswordEncoder passwordEncoder) {
        String randomPassword = new RandomCodeGenerator().generate(8);
        this.value = passwordEncoder.encode(randomPassword);
        this.wrongCount = 0;
//        this.needChange = true;
        return randomPassword;
    }

    public void changePassword(PasswordEncoder passwordEncoder, String rawPassword) {
        this.value = passwordEncoder.encode(rawPassword);
        this.wrongCount = 0;
//        this.needChange = false;
    }
}
