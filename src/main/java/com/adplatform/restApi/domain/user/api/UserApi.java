package com.adplatform.restApi.domain.user.api;

import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserApi {
    private final UserRepository userRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<UserDto.Response.Detail> search(@PageableDefault Pageable pageable) {
        return PageDto.create(this.userRepository.search(pageable));
    }
}
