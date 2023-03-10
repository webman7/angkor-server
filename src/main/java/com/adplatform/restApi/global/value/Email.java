package com.adplatform.restApi.global.value;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author junny
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Email {
    @javax.validation.constraints.Email
    @Column(name = "email")
    private String address;

    public String getId() {
        return this.address.equals("") ? "" : this.address.substring(0, this.atSignIndexOf());
    }

    public String getHost() {
        return address.equals("") ? "" : this.address.substring(this.atSignIndexOf() + 1);
    }

    private int atSignIndexOf() {
        return this.address.indexOf("@");
    }
}
