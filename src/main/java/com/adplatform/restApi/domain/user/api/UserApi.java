package com.adplatform.restApi.domain.user.api;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.dao.mapper.UserQueryMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.dto.user.UserMapper;
import com.adplatform.restApi.domain.user.service.UserFindUtils;
import com.adplatform.restApi.global.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final UserMapper userMapper;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/search")
//    public PageDto<UserDto.Response.Detail> search(@PageableDefault Pageable pageable) {
//        return PageDto.create(this.userRepository.search(pageable));
//    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{id}")
//    public UserDto.Response.Detail findById(@PathVariable Integer id) {
//        User user = UserFindUtils.findByIdOrElseThrow(id, this.userRepository);
//        List<Integer> userRoles = this.userRepository.findByUserRoles(id);
//        CompanyDto.Response.Detail company = this.companyMapper.toDetailResponse(CompanyFindUtils.findByIdOrElseThrow(user.getCompany().getId(), this.companyRepository));
//        return this.userMapper.toDetailResponse(user, userRoles, company);
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<UserDto.Response.Search> search(
            UserDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(new PageImpl<>(
                this.userQueryMapper.search(request, pageable),
                pageable,
                this.userQueryMapper.countSearch(request)
        ));
    }
}
