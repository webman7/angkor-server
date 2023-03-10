package com.adplatform.restApi.global.config.security.aop;

import com.adplatform.restApi.domain.adaccount.domain.AdAccountUser;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountUserAuthorizationException;
import com.adplatform.restApi.domain.adaccount.dao.user.AdAccountUserRepository;
import com.adplatform.restApi.domain.adaccount.exception.AdAccountUserNotFoundException;
import com.adplatform.restApi.domain.adaccount.service.AdAccountUserQueryUtils;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;

/**
 * 광고 계정 인가 유틸 클래스.
 *
 * @author Seohyun Lee
 * @since 1.0
 */
public class AdAccountUserValidationUtil {
    /**
     * 광고 계정 아이디와, 유저 아이디를 통해 해당 광고 계정에 접근할 수 있는 권한이 있는지 검증한다.
     * <p>
     * 해당 광고 계정에 접근하기 위한 권한이 있기 위해서는 {@link AdAccountUser AdAccountUser}가 존재해야하며,
     * {@link  AdAccountUser.Status requestStatus}가
     * {@link AdAccountUser.Status#Y Y}이어야 한다.
     * <p>
     * 만약 {@link AdAccountUser AdAccountUser}가 {@code null} 이라면
     * {@link AdAccountUserNotFoundException} 엑셉션이 발생된다.
     * <p>
     * 만약 {@link AdAccountUser.Status Status}가
     * {@link AdAccountUser.Status#Y Y}
     * 가 아니라면 {@link AdAccountUserAuthorizationException} 엑셉션이 발생된다.
     *
     * @param adAccountId
     * @exception AdAccountUserNotFoundException 해당 광고 계정에 접근 권한이 없습니다.
     * @exception AdAccountUserAuthorizationException 해당 광고 계정에 접근 권한이 없습니다.
     */
    public static void validateAdAccountUser(Integer adAccountId, AdAccountUserRepository adAccountUserRepository) {
        AdAccountUserQueryUtils.findByAdAccountIdAndUserIdOrElseThrow(
                        adAccountId,
                        SecurityUtils.getLoginUserNo(),
                        adAccountUserRepository)
                .validateStatus();
    }
}
