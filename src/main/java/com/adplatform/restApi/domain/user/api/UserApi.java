package com.adplatform.restApi.domain.user.api;

import com.adplatform.restApi.domain.company.dao.CompanyRepository;
import com.adplatform.restApi.domain.company.dto.CompanyDto;
import com.adplatform.restApi.domain.company.dto.CompanyMapper;
import com.adplatform.restApi.domain.company.service.CompanyFindUtils;
import com.adplatform.restApi.domain.history.dao.user.UserHistoryRepository;
import com.adplatform.restApi.domain.history.domain.UserHistory;
import com.adplatform.restApi.domain.history.domain.UserLoginHistory;
import com.adplatform.restApi.domain.history.dto.user.UserHistoryDto;
import com.adplatform.restApi.domain.history.dto.user.UserHistoryMapper;
import com.adplatform.restApi.domain.history.dto.user.UserLoginHistoryDto;
import com.adplatform.restApi.domain.user.dao.UserRepository;
import com.adplatform.restApi.domain.user.dao.mapper.UserSaveQueryMapper;
import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.domain.user.dto.auth.AuthDto;
import com.adplatform.restApi.domain.user.dto.user.UserDto;
import com.adplatform.restApi.domain.user.dto.user.UserMapper;
import com.adplatform.restApi.domain.user.service.UserFindUtils;
import com.adplatform.restApi.domain.user.dao.mapper.UserQueryMapper;
import com.adplatform.restApi.domain.user.service.UserQueryService;
import com.adplatform.restApi.global.config.security.util.SecurityUtils;
import com.adplatform.restApi.global.dto.PageDto;
import com.adplatform.restApi.global.util.HttpReqRespUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author junny
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserApi {
    private final UserRepository userRepository;
    private final UserQueryMapper userQueryMapper;

    private final UserSaveQueryMapper userSaveQueryMapper;
    private final UserMapper userMapper;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserQueryService userQueryService;
    private final UserHistoryMapper userHistoryMapper;
    private final UserHistoryRepository userHistoryRepository;

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/search")
//    public PageDto<UserDto.Response.Detail> search(@PageableDefault Pageable pageable) {
//        return PageDto.create(this.userRepository.search(pageable));
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDto.Response.Detail findById(@PathVariable Integer id) {
        User user = UserFindUtils.findByIdOrElseThrow(id, this.userRepository);



//        List<Integer> userRoles = this.userRepository.findByUserRoles(id);
//        CompanyDto.Response.Detail company = this.companyMapper.toDetailResponse(CompanyFindUtils.findByIdOrElseThrow(user.getCompany().getId(), this.companyRepository));
        return this.userMapper.toDetailResponse(user);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/search")
//    public PageDto<UserDto.Response.Search> search(
//            UserDto.Request.Search request,
//            @PageableDefault Pageable pageable) {
//        return PageDto.create(new PageImpl<>(
//                this.userQueryMapper.search(request, pageable),
//                pageable,
//                this.userQueryMapper.countSearch(request)
//        ));
//    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public PageDto<UserDto.Response.Search> search(
            UserDto.Request.Search request,
            @PageableDefault Pageable pageable) {
        return PageDto.create(this.userRepository.userSearch(request, pageable));
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/modify")
    public void modify(@RequestBody @Valid UserDto.Request.Modify request) {
        User user = this.userQueryService.findByIdOrElseThrow(request.getId());

        UserHistoryDto.Request.Save history = new UserHistoryDto.Request.Save();
        history.setUserNo(user.getId());
        history.setUserId(user.getLoginId());
        history.setUserName(user.getName());
        history.setPhone(user.getPhone());
        history.setActive(user.getActive());
        history.setPwdWrongCnt(user.getPassword().getWrongCount());
        history.setPwdUpdatedAt(user.getPassword().getPwdUpdatedAt());
        history.setRegUserNo(user.getCreatedUserNo());
        history.setCreatedAt(user.getCreatedAt());
        history.setStatusChangedUserNo(user.getStatusChangedUserNo());
        history.setStatusChangedAt(user.getStatusChangedAt());
        history.setUpdUserNo(user.getUpdatedUserNo());
        history.setUpdatedAt(user.getUpdatedAt());
        UserHistory userHistory = this.userHistoryMapper.toEntity(history);
        this.userHistoryRepository.save(userHistory);

        this.userSaveQueryMapper.modify(request, SecurityUtils.getLoginUserNo());
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/my/modify")
    public void myInfoModify(@RequestBody @Valid UserDto.Request.MyInfoModify request) {

        User user = this.userQueryService.findByIdOrElseThrow(SecurityUtils.getLoginUserNo());

        UserHistoryDto.Request.Save history = new UserHistoryDto.Request.Save();
        history.setUserNo(user.getId());
        history.setUserId(user.getLoginId());
        history.setUserName(user.getName());
        history.setPhone(user.getPhone());
        history.setActive(user.getActive());
        history.setPwdWrongCnt(user.getPassword().getWrongCount());
        history.setPwdUpdatedAt(user.getPassword().getPwdUpdatedAt());
        history.setRegUserNo(user.getCreatedUserNo());
        history.setCreatedAt(user.getCreatedAt());
        history.setStatusChangedUserNo(user.getStatusChangedUserNo());
        history.setStatusChangedAt(user.getStatusChangedAt());
        history.setUpdUserNo(user.getUpdatedUserNo());
        history.setUpdatedAt(user.getUpdatedAt());
        UserHistory userHistory = this.userHistoryMapper.toEntity(history);
        this.userHistoryRepository.save(userHistory);

        this.userSaveQueryMapper.myInfoModify(request, SecurityUtils.getLoginUserNo());
    }
}
