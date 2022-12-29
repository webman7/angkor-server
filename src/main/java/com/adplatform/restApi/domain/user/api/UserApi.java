package com.adplatform.restApi.domain.user.api;

import com.adplatform.restApi.domain.adgroup.dto.adgroup.AdGroupDto;
import com.adplatform.restApi.domain.campaign.dto.AdvertiserSearchRequest;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.dao.mapper.UserQueryMapper;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.dto.user.UserSearchRequest;
import com.adplatform.restApi.global.config.security.aop.AuthorizedAdAccount;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserApi {
    private final UserRepository userRepository;
    private final UserQueryMapper userQueryMapper;

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/search")
//    public PageDto<UserDto.Response.Detail> search(@PageableDefault Pageable pageable) {
//        return PageDto.create(this.userRepository.search(pageable));
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<UserDto.Response.Search> search(
            UserSearchRequest request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.userQueryMapper.search(request, pageable),
                pageable,
                this.userQueryMapper.countSearch(request)
        ));
    }
}
