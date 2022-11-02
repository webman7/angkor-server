package com.adplatform.restApi.domain.user.api;

import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserApi {
    private final UserRepository userRepository;

    @GetMapping("/search")
    public ResponseEntity<PageDto<UserDto.Response.Detail>> search(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(PageDto.create(this.userRepository.search(pageable)));
    }
}
